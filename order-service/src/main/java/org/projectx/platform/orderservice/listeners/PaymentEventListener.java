package org.projectx.platform.orderservice.listeners;

import org.projectx.platform.orderservice.event.PaymentResponseEvent;

public interface PaymentEventListener {

    void onPaymentSuccess(PaymentResponseEvent paymentResponseEvent);

    void onPaymentFailed(PaymentResponseEvent paymentResponseEvent);
}
