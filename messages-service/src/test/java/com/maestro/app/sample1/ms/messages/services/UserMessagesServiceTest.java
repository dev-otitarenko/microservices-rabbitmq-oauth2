package com.maestro.app.sample1.ms.messages.services;

import com.maestro.app.sample1.ms.messages.entities.UserMessages;
import com.maestro.app.sample1.ms.messages.repositories.UserMessagesRepository;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.auth.AuthJwtOrganization;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.exceptions.EntityRecordNotFound;
import com.maestro.app.utils.queue.QueueUserMessage;
import com.maestro.app.utils.types.QueueMessageState;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Maestro on 10-Oct-2020.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserMessagesServiceTest {
    @Autowired
    private UserMessagesService messagesService;
    @Autowired
    private UserMessagesRepository messagesRepository;

    private final String code = CommonUtils.generateGuid();
    private final String iduser = "0";

    @Test
    @DisplayName("Obtaining the number of user messages")
    @Order(1)
    void getCountMessages() {
        long count = messagesService.getCountMessages(iduser);
        assertTrue(count >= 0);
    }

    @Test
    @DisplayName("Obtaining the list of user messages")
    @Order(2)
    void getListMessages() {
        List<UserMessages> lst = messagesService.getListMessages(iduser);
        assertTrue(lst.size() > 0);
        UserMessages ret = lst.stream().filter(m -> m.getCode().equals(code)).findFirst().orElse(null);
        assertThat(ret).isNotNull();
    }

    @Test
    @DisplayName("Trying to delete the message with invalid message id")
    @Order(3)
    void deleteNoExistingMessage() {
        Throwable exception = assertThrows(EntityRecordNotFound.class, () -> {
            AuthUser authUser = getAuthUser(iduser, "TEST-USER", 0, iduser, "TEST-ORG", 0);
            messagesService.deleteMessage(authUser, "*");
        });

        assertThat(exception).isNotNull();
        System.out.println(exception.getMessage());
    }

    @Test
    @DisplayName("Trying to delete the message using invalid user")
    @Order(4)
    void deleteMessageForOtherUser() {
        Throwable exception = assertThrows(EntityRecordNotFound.class, () -> {
            AuthUser authUser = getAuthUser("*", "TEST-USER", 0, iduser, "TEST-ORG", 0);
            messagesService.deleteMessage(authUser, code);
        });

        assertThat(exception).isNotNull();
        System.out.println(exception.getMessage());
    }

    @Test
    @DisplayName("Removing messages specified in the list of ids")
    @Transactional
    @Order(5)
    void deleteMessages() {
        try {
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String _code = CommonUtils.generateGuid();
                UserMessages evt = new UserMessages();
                evt.setCode(_code);
                evt.setIdUser(iduser);
                evt.setTitle("Test message #" + i);
                evt.setMessage("Message for user");
                evt.setExecutionTime((double) 0);
                evt.setDateRec(new Date());
                evt.setState(1);
                messagesRepository.save(evt);

                ids.add(_code);
            }

            AuthUser authUser = getAuthUser(iduser, "TEST-USER", 0, iduser, "TEST-ORG", 0);
            messagesService.deleteMessages(authUser, ids);
        } catch (EntityRecordNotFound e) {
            fail("deleteMessages failed");
        }
    }

    @Test
    @DisplayName("Removing messages specified in the list of ids")
    @Transactional
    @Order(6)
    void deleteAllMessages() {
        for (int i = 0; i < 10; i++) {
            String _code = CommonUtils.generateGuid();
            UserMessages evt = new UserMessages();
            evt.setCode(_code);
            evt.setIdUser(iduser);
            evt.setTitle("Test message #" + i);
            evt.setMessage("Message for user");
            evt.setExecutionTime((double) 0);
            evt.setDateRec(new Date());
            evt.setState(1);
            messagesRepository.save(evt);
        }

        AuthUser authUser = getAuthUser(iduser, "TEST-USER", 0, iduser, "TEST-ORG", 0);
        messagesService.deleteAllMessages(authUser);
    }

    @Test
    @DisplayName("Trying to delete the specific message using valid user")
    @Order(5)
    void deleteMessage() {
        try {
            AuthUser authUser = getAuthUser(iduser, "TEST-USER", 0, iduser, "TEST-ORG", 0);
            messagesService.deleteMessage(authUser, code);

            assertTrue(true);
        }
        catch (EntityRecordNotFound e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    @DisplayName("Saving the message")
    @Order(0)
    void saveMessage() {
        QueueUserMessage evt = new QueueUserMessage();
        evt.setUser(getAuthUser(iduser, "TEST-USER", 0, iduser, "TEST-ORG", 0));
        evt.setCode(code);
        evt.setTitle("Test message");
        evt.setState(QueueMessageState.FAILURE);
        evt.setExecutionTime(0);

        messagesService.saveMessage(evt);

        UserMessages evt1 = messagesRepository.findById(code).orElse(null);
        assertNotNull(evt1);
        assertEquals(evt1.getCode(), evt.getCode());
        assertEquals(evt1.getMessage(), evt.getMessage());
        assertEquals(evt1.getTitle(), evt.getTitle());
        assertEquals(evt1.getIdUser(), evt.getUser().getId());
    }

    private AuthUser getAuthUser(String idUser, String userNm,  int typeAdmin, String idOrg, String orgNm, int orgLevel) {
        AuthUser user = new AuthUser();
        user.setId(idUser);
        user.setUsername(userNm);
        user.setTypeAdmin(typeAdmin);
        AuthJwtOrganization org = new AuthJwtOrganization();
        org.setId(idOrg);
        org.setName(orgNm);
        user.setOrganization(org);

        return user;
    }
}