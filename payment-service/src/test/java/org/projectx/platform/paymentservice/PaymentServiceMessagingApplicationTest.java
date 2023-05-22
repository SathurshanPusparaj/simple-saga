package org.projectx.platform.paymentservice;

import io.quarkus.test.junit.QuarkusTest;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class PaymentServiceMessagingApplicationTest {

    @Inject
    PaymentServiceMessagingApplication application;

    @Test
    void test() {

    }
}
