package org.projectx.platform.orderservice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.projectx.platform.orderservice.entity.Outbox;
import org.projectx.platform.orderservice.event.PaymentRequestEvent;
import org.projectx.platform.orderservice.service.OutBoxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OutboxMessageHandler {

    @Value("${kafka.publisher.topic.name}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper mapper;
    private final OutBoxService outBoxService;

    public OutboxMessageHandler(KafkaTemplate<String, Object> kafkaTemplate,
                                ObjectMapper mapper,
                                OutBoxService outBoxService) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
        this.outBoxService = outBoxService;
    }

    @Scheduled(fixedDelay = 2000)
    void sendMessages() {
        List<Outbox> entities = outBoxService.getNotTransferredMessages();
        for (Outbox outbox : entities) {
            try {
                PaymentRequestEvent paymentRequestEvent = mapper.readValue(outbox.getContent(), PaymentRequestEvent.class);
                kafkaTemplate.send(topic, paymentRequestEvent);
                outbox.setProcessed(LocalDateTime.now());
                outBoxService.save(outbox);
            } catch (JsonProcessingException e) {
                log.error("unable to process outbox : {}", outbox.getId());
            }
        }
    }

    //we can use spring batch for send or delete Messages from outbox or CDC
    @Scheduled(fixedDelay = 5000)
    void deleteMessages() {
        List<Outbox> entities = outBoxService.getTransferredMessages();
        outBoxService.deleteMessages(entities);
    }
}
