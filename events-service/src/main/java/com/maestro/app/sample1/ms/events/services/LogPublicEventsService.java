package com.maestro.app.sample1.ms.events.services;

import com.maestro.app.sample1.ms.events.entities.LogPublicEvents;
import com.maestro.app.sample1.ms.events.repositories.LogPublicEventsRepository;
import com.maestro.app.sample1.ms.events.specifications.LogPublicEventsSpecification;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.data.specifications.FilterUtils;
import com.maestro.app.utils.queue.QueueLogPublicEvt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LogPublicEventsService {
    private final LogPublicEventsRepository evtRepository;

    public LogPublicEventsService (LogPublicEventsRepository evtRepository) {
        this.evtRepository = evtRepository;
    }

    /**
     * Getting the list of LogPublicEvents data for UI "List of public events"
     *
     * @param user The authenticated user
     * @param pageable The object with page variables
     * @param search The string with search parameters
     * @return Page<LogPublicEvents> The lest of data for pageable table object in UI
     */
    public Page<LogPublicEvents> getListEvents(final AuthUser user, Pageable pageable, String search) {
        return evtRepository.findAll(new LogPublicEventsSpecification(user, FilterUtils.buildMapCriterias(search)), pageable);
    }

    /**
     * Saving a document public event. This event must be provided in doc-service over RabbitMQ
     *
     * @param prm The event parameter
     */
    @Transactional
    public void savePublicEvt(final QueueLogPublicEvt prm) {
        LogPublicEvents evt = new LogPublicEvents();
        evt.setCode(CommonUtils.generateGuid());
        evt.setMode(prm.getMode().getValue());
        evt.setName(prm.getName());
        evt.setDescription(prm.getDescription());
        evt.setIpaddress(prm.getIp_address());
        evt.setCountryCode(prm.getCountryCode());
        evt.setCountryName(prm.getCountryName());
        evt.setCityName(prm.getCity());

        this.evtRepository.save(evt);
    }
}
