package com.maestro.app.sample1.ms.events.services;

import com.maestro.app.sample1.ms.events.entities.LogPrivateEvents;
import com.maestro.app.sample1.ms.events.repositories.LogPrivateEventsRepository;
import com.maestro.app.sample1.ms.events.specifications.LogPrivateEventsSpecification;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.data.specifications.FilterUtils;
import com.maestro.app.utils.queue.QueueLogPrivateEvt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LogPrivateEventsService {
    private final LogPrivateEventsRepository evtRepository;

    public LogPrivateEventsService (LogPrivateEventsRepository logPrivateRepository) {
        this.evtRepository = logPrivateRepository;
    }

    /**
     * Getting the list of LogPublicEvents data for UI "List of private events"
     *
     * @param user The authenticated user
     * @param pageable The object with page variables
     * @param search The string with search parameters
     * @return Page<LogPrivateEvents> The lest of data for pageable table object in UI
     */
    public Page<LogPrivateEvents> getListEvents(final AuthUser user, Pageable pageable, String search) {
        return evtRepository.findAll(new LogPrivateEventsSpecification(user, FilterUtils.buildMapCriterias(search)), pageable);
    }

    /**
     * Saving a document private event. This event must be provided in doc-service over RabbitMQ
     *
     * @param prm The event parameter
     */
    @Transactional
    public void savePrivateEvt(final AuthUser user, final QueueLogPrivateEvt prm) {
        LogPrivateEvents evt = new LogPrivateEvents();
        evt.setCode(CommonUtils.generateGuid());
        evt.setMode(prm.getMode().getValue());
        evt.setName(prm.getName());
        evt.setDescription(prm.getDescription());
        evt.setIduser(user.getId());
        evt.setUsername(user.getUsername());

        this.evtRepository.save(evt);
    }
}
