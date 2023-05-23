package org.projectx.platform.orderservice.controller;

import org.projectx.platform.orderservice.common.DataMapper;
import org.projectx.platform.orderservice.common.OrderStatus;
import org.projectx.platform.orderservice.dto.OrderDTO;
import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.entity.OrderEntity;
import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.entity.UserEntity;
import org.projectx.platform.orderservice.handler.OrderHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {
    private final OrderHandler orderHandler;

    public OrderController(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @PostMapping("/orders")
    public OrderDTO saveOrder(@RequestBody OrderDTO order) {
        return DataMapper.convertOrderEntityToOrderDTO(orderHandler.saveOrder(order));
    }

    /**
     * Create and returns a dummy order
     * @return
     */
    @GetMapping("/orders")
    public OrderEntity getOrder() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setId(1L);
        itemEntity1.setName("Apple");
        itemEntity1.setPrice(125.12);
        itemEntity1.setQuantity(100);
        return new OrderEntity(new UserEntity(1L, null, null),
                itemEntity1, 95, 95 * itemEntity1.getPrice(),
                new TrackerEntity(UUID.randomUUID(), OrderStatus.PENDING));
    }
}
