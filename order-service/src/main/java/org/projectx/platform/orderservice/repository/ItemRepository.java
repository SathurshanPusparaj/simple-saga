package org.projectx.platform.orderservice.repository;

import org.projectx.platform.orderservice.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
}