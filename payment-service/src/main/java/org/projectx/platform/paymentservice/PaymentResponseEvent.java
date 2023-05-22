package org.projectx.platform.paymentservice;
import java.util.UUID;

public class PaymentResponseEvent {
    private UUID payment_id;

    private Long order_id;

    private PaymentStatus status;

    public UUID getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(UUID payment_id) {
        this.payment_id = payment_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public PaymentResponseEvent() {
    }

    public PaymentResponseEvent(UUID payment_id, Long order_id, PaymentStatus status) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.status = status;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
