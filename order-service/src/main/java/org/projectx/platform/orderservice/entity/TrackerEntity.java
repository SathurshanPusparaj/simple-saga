package org.projectx.platform.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.projectx.platform.orderservice.common.OrderStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class TrackerEntity {

    @Id
    @NonNull
    @Column(name = "id", nullable = false)
    private UUID id;

    @NonNull
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
