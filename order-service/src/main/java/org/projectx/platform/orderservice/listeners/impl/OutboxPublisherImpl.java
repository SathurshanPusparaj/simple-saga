package org.projectx.platform.orderservice.listeners.impl;

import org.projectx.platform.orderservice.entity.Outbox;
import org.projectx.platform.orderservice.listeners.OutboxEventPublisher;
import org.projectx.platform.orderservice.service.OutBoxService;
import org.springframework.stereotype.Component;

@Component
public class OutboxPublisherImpl implements OutboxEventPublisher {

    private OutBoxService outBoxService;

    public OutboxPublisherImpl(OutBoxService outBoxService) {
        this.outBoxService = outBoxService;
    }

    @Override
    public void publish(Outbox entity) {
        outBoxService.save(entity);
    }
}
