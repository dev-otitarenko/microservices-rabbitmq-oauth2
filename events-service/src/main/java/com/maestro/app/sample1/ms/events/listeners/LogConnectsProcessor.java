package com.maestro.app.sample1.ms.events.listeners;

import com.maestro.app.sample1.ms.events.services.LogConnectEventsService;
import com.maestro.app.sample1.ms.events.utils.Constants;
import com.maestro.app.utils.queue.QueueLogConnectEvt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = Constants.QUEUE_LOGSCONNECTS_NAME)
public class LogConnectsProcessor {
    private final LogConnectEventsService evtService;

    public LogConnectsProcessor(LogConnectEventsService logService) {
        this.evtService = logService;
    }

    @RabbitHandler
    public void processEvent(final QueueLogConnectEvt event) {
        log.debug(" -> [LogConnectsProcessor]  message [{}] received", event);
        evtService.saveConnectEvt(event);
    }
}
