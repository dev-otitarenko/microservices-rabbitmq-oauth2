package com.maestro.app.sample1.ms.messages.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maestro.app.sample1.ms.messages.entities.UserMessages;
import com.maestro.app.sample1.ms.messages.repositories.UserMessagesRepository;
import com.maestro.app.sample1.ms.messages.utils.AuthenticationToken;
import com.maestro.app.sample1.ms.messages.utils.ExtensionUtils;
import com.maestro.app.sample1.ms.messages.utils.RestTemplateUtils;
import com.maestro.app.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Maestro on Sep-2020.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageController_IT {
    TestRestTemplate template;
    AuthenticationToken token;

    @Value("${auth-server.url}")
    private String authEndpoint;
    @Value("${auth-server.user}")
    private String authUserName;
    @Value("${auth-server.secret}")
    private String authUserPass;

    @Value("${testuser.name}")
    private String appuserName;
    @Value("${testuser.psw}")
    private String appuserPass;


    @Autowired
    UserMessagesRepository messagesRepository;

    @Autowired
    private MockMvc mockMvc;
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    public void setup() {
        template = new TestRestTemplate();
        token = RestTemplateUtils.getAuthentificationToken(this.authEndpoint, this.authUserName, this.authUserPass, template, this.appuserName, this.appuserPass);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("Gets the list of user messages")
    @Order(2)
    void getUserMessages() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", RestTemplateUtils.getAuthBearer(token))
                        .content(ExtensionUtils.requestBody(null)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();;
        Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());

        ObjectMapper mapper = new ObjectMapper();
        List<Object> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Object>>() {});

        assertTrue(response.size() >= 0);

        System.out.println(" Size: " + response.size());
        response.forEach(System.out::println);
    }

    @Order(3)
    @DisplayName("Gets the number of messages for the authenticated user")
    @Test
    void countUserMessages() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", RestTemplateUtils.getAuthBearer(token))
                        .content(ExtensionUtils.requestBody(null)))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("1"))
                .andReturn();;


        Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());

        Long response = ExtensionUtils.parseResponse(mvcResult, Long.class);
        assertTrue(response >= 0);
    }

    @Test
    @DisplayName("Deletes the specific message for the authenticated user")
    @Order(4)
    void deleteUserMessage() throws Exception {
        String code = CommonUtils.generateGuid(),
               iduser = "A1AD6F48D89E44139E11C3F3402A3B89";
        UserMessages evt = new UserMessages();
        evt.setCode(code);
        evt.setIdUser(iduser);
        evt.setTitle("Test message");
        evt.setMessage("Message for user");
        evt.setExecutionTime((double) 0);
        evt.setDateRec(new Date());
        evt.setState(1);
        messagesRepository.save(evt);

        final MvcResult mvcResult = mockMvc.perform(
                        delete("/{id}", code)
                            .header("Authorization", RestTemplateUtils.getAuthBearer(token))
                            .content(ExtensionUtils.requestBody(null)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();
    }
}