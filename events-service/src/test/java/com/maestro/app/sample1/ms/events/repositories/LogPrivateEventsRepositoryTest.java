package com.maestro.app.sample1.ms.events.repositories;

import com.maestro.app.sample1.ms.events.entities.LogPrivateEvents;
import com.maestro.app.utils.CommonUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Maestro on Sep-2020.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogPrivateEventsRepositoryTest {
    @Autowired
    private LogPrivateEventsRepository logRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String IdEvent;

    @BeforeAll
    public void setup() {
        IdEvent = CommonUtils.generateGuid();
    }

    @AfterAll
    public void stop() {
        jdbcTemplate.update("delete from logs_events_private where id = ?", new Object[]{ IdEvent });
    }

    @Test
    @Order(1)
    @DisplayName("Retrieving the data from the private log")
    void test_getLogsPrivateEvents () {
        Page<LogPrivateEvents> list = logRepository.findAll(null, PageRequest.of(0, 20, Sort.by("dateRec").descending()));

        assertTrue(list.getTotalPages() >= 0);
        assertTrue(list.getTotalElements() >= 0);
        assertTrue(list.getNumberOfElements() >= 0);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(String.format(" Page info: Count: %d, Pages: %d, Page rows size: %d", list.getTotalElements(), list.getTotalPages(), list.getNumberOfElements()));
        list.getContent().stream().forEach(rdata -> {
            StringBuilder sb = new StringBuilder();
            sb
                .append(" * Processing record: {")
                .append(rdata)
                .append(" }");
            System.out.println(sb.toString());
        });
        stopWatch.stop();

        System.out.println(String.format("Method %s executed within $d miliseconds.", "pageDocuments()", stopWatch.getTotalTimeMillis()));
    }

    @Test
    @Order(0)
    @DisplayName("Saving the private event")
    void savePrivateEvt() {
        LogPrivateEvents evt = new LogPrivateEvents();
        evt.setCode(IdEvent);
        evt.setMode(1);
        evt.setName(CommonUtils.generateGuid());
        evt.setDescription("TEST");
        evt.setIduser("*");
        evt.setUsername("TEST-USER");

        logRepository.save(evt);

        LogPrivateEvents savedEvt = logRepository.findById(evt.getCode()).orElse(null);
        assertNotNull(savedEvt);
        assertEquals(savedEvt.getName(), evt.getName());
        assertEquals(savedEvt.getMode(), evt.getMode());
        assertEquals(savedEvt.getIduser(), evt.getIduser());
        assertEquals(savedEvt.getUsername(), evt.getUsername());
    }
}