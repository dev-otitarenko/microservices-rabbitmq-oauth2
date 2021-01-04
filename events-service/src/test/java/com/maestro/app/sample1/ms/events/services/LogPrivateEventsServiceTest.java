package com.maestro.app.sample1.ms.events.services;

import com.maestro.app.sample1.ms.events.entities.LogPrivateEvents;
import com.maestro.app.sample1.ms.events.utils.TestUtils;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.CommonUtils;
import com.maestro.app.utils.queue.QueueLogPrivateEvt;
import com.maestro.app.utils.types.QueueEventType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Maestro on Sep-2020.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogPrivateEventsServiceTest {
    @Autowired
    private LogPrivateEventsService logService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String IdAdminUser;
    private String IdOrgAdminUser;
    private String IdUser;
    private final String IdOrg = TestUtils.PRIME_IDORG;

    @BeforeAll
    public void setup() {
        IdAdminUser = CommonUtils.generateGuid();
        IdOrgAdminUser = CommonUtils.generateGuid();
        IdUser = CommonUtils.generateGuid();

        jdbcTemplate.update(TestUtils.SQL_USER_CREATE,
                new Object[]{ IdAdminUser, "TEST-USER-" + IdAdminUser, "TEST-USER-" + IdAdminUser, "test-user." + IdAdminUser + "@domain.dev", 1, IdOrg, IdOrg });
        jdbcTemplate.update(TestUtils.SQL_ROLE_CREATE, new Object[] { IdAdminUser, 1, new Date() } );

        jdbcTemplate.update(TestUtils.SQL_USER_CREATE,
                new Object[]{ IdOrgAdminUser, "TEST-USER-" + IdOrgAdminUser, "TEST-USER-" + IdOrgAdminUser, "test-user." + IdAdminUser + "@domain.dev", 1, IdOrg, IdOrg });
        jdbcTemplate.update(TestUtils.SQL_ROLE_CREATE, new Object[] { IdOrgAdminUser, 2, new Date() } );

        jdbcTemplate.update(TestUtils.SQL_USER_CREATE,
                new Object[]{ IdUser, "TEST-USER-" + IdUser, "TEST-USER-" + IdUser, "test-user." + IdUser + "@domain.dev", 1, IdOrg, IdOrg });
    }

    @AfterAll
    public void end() {
        jdbcTemplate.update(TestUtils.SQL_USER_DELETE, new Object[]{ IdAdminUser });
        jdbcTemplate.update(TestUtils.SQL_USER_DELETE, new Object[]{ IdOrgAdminUser });
        jdbcTemplate.update(TestUtils.SQL_USER_DELETE, new Object[]{ IdUser });
    }

    @Test
    @Order(0)
    public void saveEvent() {
        AuthUser authUser = TestUtils.queryAuthUser(jdbcTemplate, IdUser);

        QueueLogPrivateEvt prm = new QueueLogPrivateEvt();
        prm.setName(CommonUtils.generateGuid());
        prm.setDescription("Test");
        prm.setMode(QueueEventType.SIMPLE_MESSAGE);
        prm.setUser(authUser);

        logService.savePrivateEvt(authUser, prm);
    }

    @Test
    @Order(1)
    public void test_getLogPrivateEvt_PrimeOrgAdmin () {
        AuthUser authUser = TestUtils.queryAuthUser(jdbcTemplate, IdAdminUser);

        Page<LogPrivateEvents> list = logService.getListEvents(authUser, PageRequest.of(0, 20, Sort.by("dateRec").descending()), "");
        assertTrue(list.getTotalPages() > 0);
        assertTrue(list.getTotalElements() > 0);
        assertTrue(list.getNumberOfElements() > 0);
    }

    @Test
    @Order(2)
    public void test_getLogPrivateEvt_OrgAdmin () {
        AuthUser authUser = TestUtils.queryAuthUser(jdbcTemplate, IdOrgAdminUser);

        Page<LogPrivateEvents> list = logService.getListEvents(authUser, PageRequest.of(0, 20, Sort.by("dateRec").descending()), "");
        assertTrue(list.getTotalPages() >= 0);
        assertTrue(list.getTotalElements() >= 0);
        assertTrue(list.getNumberOfElements() >= 0);
    }

    @Test
    @Order(3)
    public void test_getLogPrivateEvt_User () {
        AuthUser authUser = TestUtils.queryAuthUser(jdbcTemplate, IdUser);

        Page<LogPrivateEvents> list = logService.getListEvents(authUser, PageRequest.of(0, 20, Sort.by("dateRec").descending()), "");
        assertTrue(list.getTotalPages() == 0);
        assertTrue(list.getTotalElements() == 0);
        assertTrue(list.getNumberOfElements() == 0);
    }
}
