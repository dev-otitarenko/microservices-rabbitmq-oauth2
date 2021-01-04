package com.maestro.app.sample1.ms.process.task;

import com.maestro.app.utils.queue.QueueLogConnectEvt;
import com.maestro.app.utils.queue.QueueLogPrivateEvt;
import com.maestro.app.utils.types.QueueEventType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TaskService {
    private final RabbitTemplate rabbitTemplate;
    private final Exchange eventExchange;

    public TaskService(RabbitTemplate rabbitTemplate,
                       Exchange eventExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventExchange = eventExchange;
    }


    @Scheduled(fixedRate = 10000)
    public void sendTestMessages() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // form LogConnects
        QueueLogPrivateEvt evt = new QueueLogPrivateEvt();
        evt.setName("Task Service");
        evt.setDescription("Service message " + format.format(new Date()));
        evt.setMode(QueueEventType.SIMPLE_MESSAGE);

        this.rabbitTemplate.convertAndSend(this.eventExchange.getName(), "events.private.service", evt);
    }
}
