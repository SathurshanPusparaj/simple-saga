package org.projectx.platform.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    Long userId;

    Long itemId;

    int numberOfItems;

    double totalPrice;

    String cardNo;

    TrackerDTO trackerDTO;
}
