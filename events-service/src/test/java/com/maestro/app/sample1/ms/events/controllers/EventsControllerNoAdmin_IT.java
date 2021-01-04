package com.maestro.app.sample1.ms.events.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Maestro on Sep-2020.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EventsControllerNoAdmin_IT {
//     TestRestTemplate template;
//     AuthenticationToken token;

//     @Value("${auth-server.url}")
//     private String authEndpoint;
//     @Value("${auth-server.user}")
//     private String authUserName;
//     @Value("${auth-server.secret}")
//     private String authUserPass;

//     @Value("${user2.name}")
//     private String appuserName;
//     @Value("${user2.psw}")
//     private String appuserPass;

//     @Autowired
//     private MockMvc mockMvc;

//     @BeforeEach
//     public void setup() {
//         template = new TestRestTemplate();
//         token = RestTemplateUtils.getAuthentificationToken(this.authEndpoint, this.authUserName, this.authUserPass, template, this.appuserName, this.appuserPass);
//         JacksonTester.initFields(this, new ObjectMapper());
//     }

//     @Test
//     @DisplayName("Retrieving events from the connects log for the authenticated NO-ADMIN user")
//     void findAllConnects() throws Exception {
//         mockMvc.perform(
//                 MockMvcRequestBuilders.get("/connects")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .header("Authorization", RestTemplateUtils.getAuthBearer(token))
//                         .content(ExtensionUtils.requestBody(null)))
//                 .andDo(print())
//                 .andExpect(status().isUnauthorized())
//                 .andReturn();
//     }

//     @Test
//     @DisplayName("Retrieving events from the public log for the authenticated NO-ADMIN user")
//     void findAllPublicEvents() throws Exception{
//         mockMvc.perform(
//                 MockMvcRequestBuilders.get("/public")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .header("Authorization", RestTemplateUtils.getAuthBearer(token))
//                         .content(ExtensionUtils.requestBody(null)))
//                 .andDo(print())
//                 .andExpect(status().isUnauthorized())
//                 .andReturn();
//     }

//     @Test
//     @DisplayName("Retrieving events from the private log for the authenticated NO-ADMIN user")
//     void findAllPrivateEvents() throws Exception {
//         mockMvc.perform(
//                 MockMvcRequestBuilders.get("/private")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .header("Authorization", RestTemplateUtils.getAuthBearer(token))
//                         .content(ExtensionUtils.requestBody(null)))
//                 .andDo(print())
//                 .andExpect(status().isUnauthorized())
//                 .andReturn();;
//     }
}