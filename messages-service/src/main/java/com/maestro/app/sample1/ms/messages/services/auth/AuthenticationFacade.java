package com.maestro.app.sample1.ms.messages.services.auth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maestro.app.utils.auth.AuthJwt;
import com.maestro.app.utils.auth.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String getAuthUserId() {
        AuthUser user = this.getAuthUser();
        return user.getId();
    }

    @Override
    public AuthUser getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String claims = JwtHelper.decode(details.getTokenValue()).getClaims();
        try {
            AuthJwt jwt = mapper.readValue(claims, AuthJwt.class);
            return new AuthUser(jwt);
        } catch (IOException e) {
            log.error ("getAuthUser() ", e);
            return null;
        }
    }


    @Override
    public boolean hasRole (String roleName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;

        return auth
                .getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equalsIgnoreCase(roleName));
    }

    @Override
    public boolean hasAnyRoles (String roleNames) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;

        String[] roles = roleNames.split(",");
        boolean ret = false;
        for (String roleName : roles) {
            ret = auth
                    .getAuthorities()
                    .stream()
                    .anyMatch(role -> role.getAuthority().equalsIgnoreCase(roleName));
            if (ret) return true;
        }
        return false;
    }
}
