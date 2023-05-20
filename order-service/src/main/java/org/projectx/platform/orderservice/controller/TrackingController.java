package org.projectx.platform.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.dto.TrackerDTO;
import org.projectx.platform.orderservice.entity.TrackerEntity;
import org.projectx.platform.orderservice.service.TrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@Slf4j
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/trackers/{id}")
    public ResponseEntity<TrackerDTO> getTracker(@PathVariable UUID id) {
        try
        {
            TrackerEntity tracker = trackingService.getTracker(id).orElseThrow(() -> new RuntimeException("Tracker id not found...!!!"));
            return ResponseEntity.ok(new TrackerDTO(tracker.getId(), tracker.getStatus()));
        } catch (Exception ex) {
            log.error("Tracking id: {} , {}", id, ex.getMessage());
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage())).build();
        }
    }
}
