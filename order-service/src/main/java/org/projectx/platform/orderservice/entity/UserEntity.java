package org.projectx.platform.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity<Long> {

    String name;

    @Column(unique = true)
    String email;

    public UserEntity(Long id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public UserEntity(Long id) {
        super(id);
    }
}
