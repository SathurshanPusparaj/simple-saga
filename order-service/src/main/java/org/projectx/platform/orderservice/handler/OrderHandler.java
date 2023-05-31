package org.projectx.platform.orderservice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.common.DataMapper;
import org.projectx.platform.orderservice.common.OrderStatus;
import org.projectx.platform.orderservice.dto.OrderDTO;
import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.entity.OrderEntity;
import org.projectx.platform.orderservice.entity.Outbox;
import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.event.PaymentRequestEvent;
import org.projectx.platform.orderservice.listeners.OutboxEventPublisher;
import org.projectx.platform.orderservice.service.ItemService;
import org.projectx.platform.orderservice.service.OrderService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class OrderHandler {

    private final OrderService orderService;

    private final ItemService itemService;

    private final ObjectMapper objectMapper;

    private final OutboxEventPublisher outboxEventPublisher;

    public OrderHandler(OrderService orderService,
                        ItemService itemService,
                        ObjectMapper objectMapper, OutboxEventPublisher outboxEventPublisher) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.objectMapper = objectMapper;
        this.outboxEventPublisher = outboxEventPublisher;
    }

    @Transactional
    public OrderEntity saveOrder(OrderDTO orderdto) {
        if (orderdto.getCardNo() == null) {
            throw new RuntimeException("Credit card not provided.");
        }

        Optional<ItemEntity> itemEntity = itemService.getItem(orderdto.getItemId());

        if (!itemEntity.isPresent()) {
           throw new RuntimeException("Invalid item");
        }

        if (itemEntity.get().getQuantity() < orderdto.getNumberOfItems()) {
            throw new RuntimeException("Invalid quantity");
        }

        OrderEntity order = DataMapper.convertOrderDTOToOrderEntity(orderdto);
        order.setTotalPrice(order.getNumberOfItems() * itemEntity.get().getPrice());

        ItemEntity item = itemEntity.get();
        item.setQuantity(item.getQuantity()-orderdto.getNumberOfItems());

        order.setItemEntity(item);

        TrackerEntity trackerEntity = new TrackerEntity(UUID.randomUUID(), OrderStatus.PENDING);
        order.setTrackingId(trackerEntity);

        OrderEntity orderEntity = orderService.saveOrder(order);

        PaymentRequestEvent paymentRequestEvent = new PaymentRequestEvent(UUID.randomUUID(), orderEntity.getId(), orderdto.getCardNo(), orderEntity.getTotalPrice());

        String value = null;
        try {
            value = objectMapper.writeValueAsString(paymentRequestEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Outbox outbox = new Outbox();
        outbox.setContent(value);
        outbox.setOccurred(LocalDateTime.now());

        outboxEventPublisher.publish(outbox);

        log.info("Order saved successfully. Sending request to payment service.");

        return orderEntity;
    }

    @Transactional
    public void completeOrder(Long orderId) {

        Optional<OrderEntity> orderEntity = orderService.getOrder(orderId);

        log.info("Order saved successfully. Sending request to payment service.");

        OrderEntity order = orderEntity.get();

        TrackerEntity trackingId = order.getTrackingId();
        order.setTrackingId(new TrackerEntity(trackingId.getId(), OrderStatus.FINALIZING));

        orderService.saveOrder(order);

        //performing some business process for 10 sec
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        order.setTrackingId(new TrackerEntity(trackingId.getId(), OrderStatus.COMPLETED));
        orderService.saveOrder(order);
    }

    public void failOrder(Long orderId) {

        Optional<OrderEntity> orderEntity = orderService.getOrder(orderId);

        OrderEntity order = orderEntity.get();

        TrackerEntity trackingId = order.getTrackingId();
        order.setTrackingId(new TrackerEntity(trackingId.getId(), OrderStatus.INVALID));

        orderService.saveOrder(order);
    }
}
