package com.maestro.app.sample1.ms.events.controllers;

import com.maestro.app.sample1.ms.events.utils.ExtensionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Maestro on Sep-2020.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EventsControlleWithNoAuthTest {
     @Autowired
     private MockMvc mockMvc;

     @Test
     @DisplayName("Trying to retrieve events from users connects log")
     void findAllConnects() throws Exception {
         mockMvc.perform(
                 MockMvcRequestBuilders.get("/connect")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(ExtensionUtils.requestBody(null)))
                 .andDo(print())
                 .andExpect(status().isUnauthorized())
                 .andReturn();
     }

     @Test
     @DisplayName("Trying to retrieve events from the public log")
     void findAllPublicEvents() throws Exception {
         mockMvc.perform(
                 MockMvcRequestBuilders.get("/public")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(ExtensionUtils.requestBody(null)))
                 .andDo(print())
                 .andExpect(status().isUnauthorized())
                 .andReturn();
     }

     @Test
     @DisplayName("Trying to retrieve events from the users private log")
     void findAllPrivateEvents() throws Exception {
         mockMvc.perform(
                 MockMvcRequestBuilders.get("/private")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(ExtensionUtils.requestBody(null)))
                 .andDo(print())
                 .andExpect(status().isUnauthorized())
                 .andReturn();
     }
}