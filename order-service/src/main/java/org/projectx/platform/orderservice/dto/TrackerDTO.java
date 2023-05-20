package org.projectx.platform.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.projectx.platform.orderservice.common.OrderStatus;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrackerDTO {

    private UUID id;

    private OrderStatus status;
}
