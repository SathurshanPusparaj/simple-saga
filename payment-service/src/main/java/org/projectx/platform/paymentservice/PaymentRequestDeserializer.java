package org.projectx.platform.paymentservice;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PaymentRequestDeserializer extends ObjectMapperDeserializer<PaymentRequestEvent> {

    public PaymentRequestDeserializer() {
        super(PaymentRequestEvent.class);
    }
}
