package org.projectx.platform.orderservice.repository;

import org.projectx.platform.orderservice.entity.Outbox;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OutBoxRepository extends CrudRepository<Outbox, Long> {

    @Query("select ou from Outbox ou where ou.processed IS NULL")
    List<Outbox> getEntities();
}
