package com.maestro.app.sample1.ms.events.listeners;

import com.maestro.app.sample1.ms.events.services.LogPublicEventsService;
import com.maestro.app.sample1.ms.events.utils.Constants;
import com.maestro.app.utils.queue.QueueLogPublicEvt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = Constants.QUEUE_LOGSPUBLIC_NAME)
public class LogPublicProcessor {
    private final LogPublicEventsService evtService;

    public LogPublicProcessor(LogPublicEventsService logService) {
        this.evtService = logService;
    }

    @RabbitHandler
    public void processEvent(final QueueLogPublicEvt event) {
        log.debug(" -> [LogPublicProcessor]  message [{}] received", event);
        evtService.savePublicEvt(event);
    }
}
