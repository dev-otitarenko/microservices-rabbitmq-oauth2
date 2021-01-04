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
class EventsController_IT {
//     TestRestTemplate template;
//     AuthenticationToken token;

//     @Value("${auth-server.url}")
//     private String authEndpoint;
//     @Value("${auth-server.user}")
//     private String authUserName;
//     @Value("${auth-server.secret}")
//     private String authUserPass;

//     @Value("${user1.name}")
//     private String appuserName;
//     @Value("${user1.psw}")
//     private String appuserPass;

//     @Autowired
//     private MockMvc mockMvc;
//     private static final String CONTENT_TYPE = "application/json";

//     @BeforeEach
//     public void setup() {
//         template = new TestRestTemplate();
//         token = RestTemplateUtils.getAuthentificationToken(this.authEndpoint, this.authUserName, this.authUserPass, template, this.appuserName, this.appuserPass);
//         JacksonTester.initFields(this, new ObjectMapper());
//     }

//     @Test
//     @DisplayName("Retrieving events for the connects log")
//     void findAllConnects() throws Exception {
//         final MvcResult mvcResult = mockMvc.perform(
//                 MockMvcRequestBuilders.get("/connects")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .header("Authorization", RestTemplateUtils.getAuthBearer(token))
//                         .content(ExtensionUtils.requestBody(null)))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andReturn();;
//        assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
//     }

//     @Test
//     @DisplayName("Retrieving events for the public log")
//     void findAllPublicEvents() throws Exception{
//         final MvcResult mvcResult = mockMvc.perform(
//                 MockMvcRequestBuilders.get("/public")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .header("Authorization", RestTemplateUtils.getAuthBearer(token))
//                         .content(ExtensionUtils.requestBody(null)))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andReturn();;
//         assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
//     }

//     @Test
//     @DisplayName("Retrieving events for the private log")
//     void findAllPrivateEvents() throws Exception {
//         final MvcResult mvcResult = mockMvc.perform(
//                 MockMvcRequestBuilders.get("/private")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .header("Authorization", RestTemplateUtils.getAuthBearer(token))
//                         .content(ExtensionUtils.requestBody(null)))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andReturn();;
//        assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
//     }
}