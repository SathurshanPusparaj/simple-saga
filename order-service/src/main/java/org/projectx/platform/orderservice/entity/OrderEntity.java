package org.projectx.platform.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseEntity<Long> {

    @ManyToOne
    UserEntity userEntity;

    @OneToOne
    @Cascade(CascadeType.PERSIST)
    ItemEntity itemEntity;

    int numberOfItems;

    double totalPrice;

    @OneToOne
    @Cascade(CascadeType.ALL)
    TrackerEntity trackingId;
}
