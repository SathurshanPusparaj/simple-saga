package org.projectx.platform.orderservice.handler;

import jakarta.transaction.Transactional;
import org.projectx.platform.orderservice.common.DataMapper;
import org.projectx.platform.orderservice.common.OrderStatus;
import org.projectx.platform.orderservice.dto.OrderDTO;
import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.entity.OrderEntity;
import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.service.ItemService;
import org.projectx.platform.orderservice.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderHandler {

    private final OrderService orderService;

    private final ItemService itemService;

    public OrderHandler(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    @Transactional
    public OrderEntity orderItem(OrderDTO orderdto) {
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

        return orderEntity;
    }
}
