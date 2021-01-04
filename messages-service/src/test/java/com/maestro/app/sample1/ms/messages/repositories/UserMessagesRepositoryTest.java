package com.maestro.app.sample1.ms.messages.repositories;

import com.maestro.app.sample1.ms.messages.entities.UserMessages;
import com.maestro.app.utils.CommonUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Maestro on Sep-2020.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMessagesRepositoryTest {
    @Autowired
    private UserMessagesRepository messagesRepository;

    private final String code = CommonUtils.generateGuid();
    private final String iduser = "0";

    @Test
    @DisplayName("Saving a message in the table")
    @Order(0)
    void saveMessage() {
        UserMessages evt = new UserMessages();
        evt.setCode(code);
        evt.setIdUser(iduser);
        evt.setTitle("Test message");
        evt.setMessage("Message for user");
        evt.setExecutionTime((double) 0);
        evt.setDateRec(new Date());
        evt.setState(1);
        messagesRepository.save(evt);

        UserMessages evt1 = messagesRepository.findById(code).orElse(null);
        assertNotNull(evt1);
        assertEquals(evt1.getCode(), evt.getCode());
        assertEquals(evt1.getMessage(), evt.getMessage());
        assertEquals(evt1.getTitle(), evt.getTitle());
        assertEquals(evt1.getIdUser(), evt.getIdUser());
    }

    @Test
    @DisplayName("Getting the count of messages in the table for the specific user")
    @Order(1)
    void countMessages() {
        long cnt = messagesRepository.countMessages(iduser);
        assertTrue(cnt > 0);
    }

    @Test
    @DisplayName("Removing a message")
    @Order(5)
    @Transactional
    void deleteMessage() {
        messagesRepository.deleteMessage(code);
        UserMessages evt1 = messagesRepository.findById(code).orElse(null);
        assertNull(evt1);
    }
}