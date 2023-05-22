package org.projectx.platform.orderservice.handler;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.common.DataMapper;
import org.projectx.platform.orderservice.common.OrderStatus;
import org.projectx.platform.orderservice.dto.OrderDTO;
import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.entity.OrderEntity;
import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.event.PaymentRequestEvent;
import org.projectx.platform.orderservice.service.ItemService;
import org.projectx.platform.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class OrderHandler {

    @Value("${kafka.publisher.topic.name}")
    private String topic;

    private final OrderService orderService;

    private final ItemService itemService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderHandler(OrderService orderService,
                        ItemService itemService, KafkaTemplate kafkaTemplate) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public OrderEntity orderItem(OrderDTO orderdto) {
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

        ItemEntity item = itemEntity.get();
        item.setQuantity(item.getQuantity()-orderdto.getNumberOfItems());

        order.setItemEntity(item);

        TrackerEntity trackerEntity = new TrackerEntity(UUID.randomUUID(), OrderStatus.PENDING);
        order.setTrackingId(trackerEntity);

        OrderEntity orderEntity = orderService.saveOrder(order);

        log.info("Order saved successfully. Sending request to payment service.");

        PaymentRequestEvent paymentRequestEvent = new PaymentRequestEvent(UUID.randomUUID(), orderEntity.getId(), orderdto.getCardNo(), orderEntity.getTotalPrice());
        kafkaTemplate.send(topic, paymentRequestEvent);

        return orderEntity;
    }
}
