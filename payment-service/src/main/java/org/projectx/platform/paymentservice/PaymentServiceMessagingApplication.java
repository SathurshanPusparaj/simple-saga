package org.projectx.platform.paymentservice;

import io.smallrye.mutiny.Uni;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.*;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * A simple event handler
 */
@ApplicationScoped
public class PaymentServiceMessagingApplication {

    private static Logger log = LoggerFactory.getLogger(PaymentServiceMessagingApplication.class);
    @Incoming("payment-in")
    @Outgoing("payment-out")
    public Uni<PaymentResponseEvent> processPayment(ConsumerRecord<String, PaymentRequestEvent> message) {
        PaymentRequestEvent event = message.value();
        log.info("incoming message : payment id: {}, order id: {}, card no: {} , price: {} ",
                event.getPayment_id(),
                event.getOrder_id(),
                event.getCardNo(),
                event.getPrice());

        return Uni.createFrom().item(new PaymentResponseEvent(event.getPayment_id(), event.getOrder_id(), generateRandomNumber() <= 5 ?  PaymentStatus.COMPLETED : PaymentStatus.FAILED))
                .onItem().delayIt().by(Duration.ofSeconds(10))
                .onItem().invoke(() -> log.info("Payment process completed"));
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 10);
    }
}
