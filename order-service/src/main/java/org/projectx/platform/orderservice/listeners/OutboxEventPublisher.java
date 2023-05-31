package org.projectx.platform.orderservice.listeners;

import org.projectx.platform.orderservice.entity.Outbox;

public interface OutboxEventPublisher {
    void publish(Outbox entity);
}
