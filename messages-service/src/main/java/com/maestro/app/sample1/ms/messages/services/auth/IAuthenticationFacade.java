package com.maestro.app.sample1.ms.messages.services.auth;

import com.maestro.app.utils.auth.AuthUser;

public interface IAuthenticationFacade {
    String getAuthUserId();

    AuthUser getAuthUser();

    boolean hasRole (String roleName);

    boolean hasAnyRoles (String roleNames);
}
