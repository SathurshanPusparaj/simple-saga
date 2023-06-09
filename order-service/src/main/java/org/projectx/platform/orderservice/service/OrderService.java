package org.projectx.platform.orderservice.service;

import org.projectx.platform.orderservice.entity.OrderEntity;
import org.projectx.platform.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public Optional<OrderEntity> getOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
