package com.maestro.app.sample1.ms.messages.services;

import com.maestro.app.sample1.ms.messages.entities.UserMessages;
import com.maestro.app.sample1.ms.messages.repositories.UserMessagesRepository;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.exceptions.EntityRecordNotFound;
import com.maestro.app.utils.queue.QueueUserMessage;
import com.maestro.app.utils.types.QueueMessageState;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class UserMessagesService {
    private final UserMessagesRepository messagesRepository;
    private final MessageSource messageSource;

    public UserMessagesService(UserMessagesRepository messagesRepository,
                               MessageSource messageSource) {
        this.messagesRepository = messagesRepository;
        this.messageSource = messageSource;
    }

    /**
     * Getting unread count of user messages.
     *
     * @param idUser ID user
     * @return Count of unread count of messages for the active user
     */
    public long getCountMessages(final String idUser) {
        return messagesRepository.countMessages(idUser);
    }

    /**
     * Getting a list of the current user messages.
     *
     * @param idUser ID user
     * @return List of messages for the current user
     */
    public List<UserMessages> getListMessages (String idUser) {
       Page<UserMessages> list = messagesRepository.getListMessages(PageRequest.of(0, 5), idUser);
       return list.getContent();
    }

    /**
     * Deleting the message record
     *
     * @param authUser The authenticated user record
     * @param id Id message
     */
    @Transactional
    public void deleteMessage(final AuthUser authUser, final String id) throws EntityRecordNotFound {
        Locale locale = LocaleContextHolder.getLocale();
        UserMessages rec = messagesRepository
                            .findById(id)
                            .orElseThrow(() -> new EntityRecordNotFound(messageSource.getMessage("message.notfound", new Object[]{ id }, locale)));
        if (rec.getIdUser().equals(authUser.getId())) {
            messagesRepository.deleteMessage(rec.getCode());
            return;
        }
        throw new EntityRecordNotFound(messageSource.getMessage("message.nogrants", new Object[]{ id }, locale));
    }

    @Transactional
    public void deleteMessages(final AuthUser authUser, final List<String> ids) throws EntityRecordNotFound {
        Locale locale = LocaleContextHolder.getLocale();
        for (String id : ids) {
            UserMessages rec = messagesRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityRecordNotFound(messageSource.getMessage("message.notfound", new Object[]{id}, locale)));
            if (rec.getIdUser().equals(authUser.getId())) {
                messagesRepository.deleteMessage(rec.getCode());
            } else {
                throw new EntityRecordNotFound(messageSource.getMessage("message.nogrants", new Object[]{id}, locale));
            }
        }
        messagesRepository.flush();
    }

    @Transactional
    public void deleteAllMessages(final AuthUser authUser) {
        messagesRepository.deleteAllMessages(authUser.getId());
        messagesRepository.flush();
    }

    /**
     * Saving a message in DB
     *
     * @param prm The parameters with message data. It must be provided from RabbitMQ queue
     */
    @Transactional
    public void saveMessage(final QueueUserMessage prm) {
        final String code = StringUtil.isBlank(prm.getCode()) ? CommonUtils.generateGuid() : prm.getCode();
        int cnt = messagesRepository.updateMessage(
                        code,
                        StringUtils.isEmpty(prm.getTitle()) ? "Where is my title??" : prm.getTitle(),
                        prm.getMessage(),
                        prm.getState() == null ? QueueMessageState.SUCCESS.getValue() : prm.getState().getValue(),
                        prm.getIdDownload(),
                        prm.getDownloadFileNm(),
                        prm.getExecutionTime(),
                        new Date());
        if (cnt == 0) { // The message does not exist
            UserMessages evt = new UserMessages();
            evt.setCode(code);
            evt.setIdUser(prm.getUser().getId());
            evt.setTitle(prm.getTitle());
            evt.setMessage(prm.getMessage());
            evt.setExecutionTime(prm.getExecutionTime());
            evt.setDateRec(new Date());
            evt.setState(prm.getState().getValue());

            this.messagesRepository.saveAndFlush(evt);
        }
    }
}
