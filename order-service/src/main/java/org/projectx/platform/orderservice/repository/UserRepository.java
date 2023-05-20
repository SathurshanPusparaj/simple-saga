package org.projectx.platform.orderservice.repository;

import org.projectx.platform.orderservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
