package org.projectx.platform.orderservice.repository;

import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrackingRepository extends CrudRepository<TrackerEntity, UUID> {
}
