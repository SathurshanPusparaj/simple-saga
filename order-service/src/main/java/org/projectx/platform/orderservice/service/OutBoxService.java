package org.projectx.platform.orderservice.service;

import org.projectx.platform.orderservice.entity.Outbox;
import org.projectx.platform.orderservice.repository.OutBoxRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutBoxService {
    private final OutBoxRepository outBoxRepository;
    public OutBoxService(OutBoxRepository outBoxRepository) {
        this.outBoxRepository = outBoxRepository;
    }
    public void save(Outbox entity) {
        outBoxRepository.save(entity);
    }
    public List<Outbox> getEntities() {
        return outBoxRepository.getEntities();
    }
}
