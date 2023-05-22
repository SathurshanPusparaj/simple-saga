package org.projectx.platform.orderservice.event;

import lombok.*;
import org.projectx.platform.orderservice.common.PaymentStatus;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequestEvent {

    private UUID payment_id;

    private Long order_id;

    private String cardNo;

    private double price;
}
