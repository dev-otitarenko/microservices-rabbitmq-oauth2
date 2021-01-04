package com.maestro.app.sample1.ms.events.repositories;

import com.maestro.app.sample1.ms.events.entities.LogConnectEvents;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LogConnectEventsRepository extends CrudRepository<LogConnectEvents, String>, JpaSpecificationExecutor<LogConnectEvents> {
}
