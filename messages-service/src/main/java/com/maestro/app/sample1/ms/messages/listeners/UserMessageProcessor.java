package com.maestro.app.sample1.ms.messages.listeners;

import com.maestro.app.sample1.ms.messages.services.UserMessagesService;
import com.maestro.app.sample1.ms.messages.utils.Constants;
import com.maestro.app.utils.queue.QueueUserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = Constants.QUEUE_USERMESSAGE_NAME)
public class UserMessageProcessor {
    private final UserMessagesService messageService;

    public UserMessageProcessor(UserMessagesService messageService) {
        this.messageService = messageService;
    }

    @RabbitHandler
    public void processEvent(final QueueUserMessage event) {
        log.debug(" -> [UserMessageProcessor]  message [{}] received", event);
        messageService.saveMessage(event);
    }
}
