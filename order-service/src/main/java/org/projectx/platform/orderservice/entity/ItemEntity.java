package org.projectx.platform.orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemEntity extends BaseEntity <Long> {

    private String name;

    private int quantity;

    private double price;
}
