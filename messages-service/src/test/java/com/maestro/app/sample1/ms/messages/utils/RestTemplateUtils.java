package com.maestro.app.sample1.ms.messages.utils;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Base64;

/**
 * Created by Maestro on Sep-2020.
 */
public class RestTemplateUtils {
    public static AuthenticationToken getAuthentificationToken(String BASE_URL, String authUser, String authPass, TestRestTemplate template, String user, String pass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(String.format("%s:%s", authUser, authPass).getBytes())));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AuthenticationToken> response = template.exchange(String.format("%s/oauth/token?username=%s&password=%s&grant_type=password", BASE_URL, user, pass), HttpMethod.POST, entity, AuthenticationToken.class);
        return response.getBody();
    }

    public static String getAuthBearer(final AuthenticationToken token) {
        return String.format("Bearer %s", token.getAccess_token());
    }
}
