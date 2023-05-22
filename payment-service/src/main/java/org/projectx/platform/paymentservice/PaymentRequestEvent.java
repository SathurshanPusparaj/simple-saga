package org.projectx.platform.paymentservice;
import java.util.UUID;

public class PaymentRequestEvent {
    private UUID payment_id;

    private Long order_id;

    private String cardNo;

    private double price;

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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
