package com.maestro.app.sample.ms.events.repositories;

import com.maestro.app.sample.ms.events.entities.LogPublicEvents;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LogPublicEventsRepository extends CrudRepository<LogPublicEvents, String>, JpaSpecificationExecutor<LogPublicEvents> {
}
