package org.projectx.platform.orderservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.common.PaymentStatus;
import org.projectx.platform.orderservice.event.PaymentResponseEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentResponseListener {

    private final ObjectMapper objectMapper;

    private final PaymentEventListener paymentEventListener;

    public PaymentResponseListener(ObjectMapper objectMapper , PaymentEventListener paymentEventListener) {
        this.objectMapper = objectMapper;
        this.paymentEventListener = paymentEventListener;
    }

    @KafkaListener(id="${kafka.consumer.group.id}", topics = "${kafka.consumer.topic.name}")
    public void receive(@Payload String message) throws JsonProcessingException {
        PaymentResponseEvent paymentResponseEvent = objectMapper.readValue(message, PaymentResponseEvent.class);

        if (PaymentStatus.COMPLETED == paymentResponseEvent.getStatus()) {
            paymentEventListener.onPaymentSuccess(paymentResponseEvent);
        } else if (PaymentStatus.FAILED == paymentResponseEvent.getStatus()) {
            paymentEventListener.onPaymentFailed(paymentResponseEvent);
        } else {
            log.error("Payment status doesn't matched.");
        }
    }
}
