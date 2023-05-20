package org.projectx.platform.orderservice.service;

import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.repository.TrackingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TrackingService {

    private final TrackingRepository trackingRepository;

    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public Optional<TrackerEntity> getTracker(UUID id) {
        return trackingRepository.findById(id);
    }
}
