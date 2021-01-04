package com.maestro.app.sample1.ms.events.services;

import com.maestro.app.sample1.ms.events.entities.LogConnectEvents;
import com.maestro.app.sample1.ms.events.repositories.LogConnectEventsRepository;
import com.maestro.app.sample1.ms.events.specifications.LogConnectEventsSpecification;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.data.specifications.FilterUtils;
import com.maestro.app.utils.queue.QueueLogConnectEvt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class LogConnectEventsService {
    private final LogConnectEventsRepository evtRepository;

    public  LogConnectEventsService (LogConnectEventsRepository logRepository) {
        this.evtRepository = logRepository;
    }

    /**
     * Getting the list of LogConnectsEvents data for UI "List of connect events"
     *
     * @param user The authenticated user
     * @param pageable The object with page variables
     * @param search The string with search parameters
     * @return Page<LogConnectEvents> The lest of data for pageable table object in UI
     */
    public Page<LogConnectEvents> getListEvents(final AuthUser user, Pageable pageable, String search) {
        return evtRepository.findAll(new LogConnectEventsSpecification(user, FilterUtils.buildMapCriterias(search)), pageable);
    }

    /**
     * Saving a connect event. This event must be provided in auth-service over RabbitMQ
     *
     * @param prm The event parameter
     */
    @Transactional
    public void saveConnectEvt(final QueueLogConnectEvt prm) {
        LogConnectEvents evt = new LogConnectEvents();
        evt.setCode(CommonUtils.generateGuid());
        evt.setIduser(prm.getIduser());
        evt.setUsername(prm.getUsername());
        evt.setIpaddress(prm.getIp_address());
        evt.setCountryCode(prm.getCountryCode());
        evt.setCountryName(prm.getCountryName());
        evt.setCityName(prm.getCity());
        evt.setDeviceDetails(prm.getDeviceDetails());
        evt.setDateRec(new Date());

        this.evtRepository.save(evt);
    }
}
