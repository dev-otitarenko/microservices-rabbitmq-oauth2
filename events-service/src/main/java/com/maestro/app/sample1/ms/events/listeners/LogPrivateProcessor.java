package com.maestro.app.sample1.ms.events.listeners;

import com.maestro.app.sample1.ms.events.services.LogPrivateEventsService;
import com.maestro.app.sample1.ms.events.utils.Constants;
import com.maestro.app.utils.queue.QueueLogPrivateEvt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = Constants.QUEUE_LOGSPRIVATE_NAME)
public class LogPrivateProcessor {
    private final LogPrivateEventsService evtService;

    public LogPrivateProcessor(LogPrivateEventsService logService) {
        this.evtService = logService;
    }

    @RabbitHandler
    public void processEvent(final QueueLogPrivateEvt event) {
        log.debug(" -> [LogPrivateProcessor]  message [{}] received", event);
        evtService.savePrivateEvt(event.getUser(), event);
    }
}
