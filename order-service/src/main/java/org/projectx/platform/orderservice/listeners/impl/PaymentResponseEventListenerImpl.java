package org.projectx.platform.orderservice.listeners.impl;

import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.event.PaymentResponseEvent;
import org.projectx.platform.orderservice.handler.OrderHandler;
import org.projectx.platform.orderservice.listeners.PaymentEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentResponseEventListenerImpl implements PaymentEventListener {

    private final OrderHandler orderHandler;

    public PaymentResponseEventListenerImpl(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Override
    public void onPaymentSuccess(PaymentResponseEvent paymentResponseEvent) {
        log.info("Payment {} success for order {}", paymentResponseEvent.getPayment_id(), paymentResponseEvent.getOrder_id());
        orderHandler.completeOrder(paymentResponseEvent.getOrder_id());
    }

    @Override//This is a compensation for payment failed by simply marking the order status to invalid
    public void onPaymentFailed(PaymentResponseEvent paymentResponseEvent) {
        log.info("Payment {} failed for order {}", paymentResponseEvent.getPayment_id(), paymentResponseEvent.getOrder_id());
        orderHandler.failOrder(paymentResponseEvent.getOrder_id());
    }
}
