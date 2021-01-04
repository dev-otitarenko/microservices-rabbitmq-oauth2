package com.maestro.app.sample1.ms.events.controllers;

import com.maestro.app.sample1.ms.events.entities.LogConnectEvents;
import com.maestro.app.sample1.ms.events.entities.LogPrivateEvents;
import com.maestro.app.sample1.ms.events.entities.LogPublicEvents;
import com.maestro.app.sample1.ms.events.services.LogConnectEventsService;
import com.maestro.app.sample1.ms.events.services.LogPrivateEventsService;
import com.maestro.app.sample1.ms.events.services.LogPublicEventsService;
import com.maestro.app.sample1.ms.events.services.auth.IAuthenticationFacade;
import com.maestro.app.utils.auth.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Operators with the system events")
@RestController
public class EventsController {
    private final LogConnectEventsService logConnectsService;
    private final LogPublicEventsService logPublicEventsService;
    private final LogPrivateEventsService logPrivateEventsService;
    private final IAuthenticationFacade authService;

    public EventsController(LogConnectEventsService logConnectsService,
                            LogPublicEventsService logPublicEventsService,
                            LogPrivateEventsService logPrivateEventsService,
                            IAuthenticationFacade authService) {
        this.logConnectsService = logConnectsService;
        this.logPublicEventsService = logPublicEventsService;
        this.logPrivateEventsService = logPrivateEventsService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('CAN_LOG_READ')")
    @GetMapping(value = "/connects")
    @ApiOperation(value = "Getting list of connect events for the authenticated user", response = Page.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "pageable",
                    required = true,
                    dataType = "Pageable",
                    value = "Page parameters"
            ),
            @ApiImplicitParam(
                    name = "search",
                    paramType = "request",
                    dataType = "String",
                    value = "Additional search criteria"
            )
    })
    public Page<LogConnectEvents> findAllConnects(Pageable pageable,
                                                  @RequestParam(value = "search", required = false) String search) {
        AuthUser user = authService.getAuthUser();
        return this.logConnectsService.getListEvents(user, pageable, search);
    }

    @PreAuthorize("hasAuthority('CAN_LOG_READ')")
    @GetMapping(value = "/public")
    @ApiOperation(value = "Getting list of document public events for the authenticated user", response = Page.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "pageable",
                    required = true,
                    dataType = "Pageable",
                    value = "Page parameters"
            ),
            @ApiImplicitParam(
                    name = "search",
                    paramType = "request",
                    dataType = "String",
                    value = "Additional search criteria"
            )
    })
    public Page<LogPublicEvents> findAllPublicEvents(Pageable pageable,
                                                     @RequestParam(value = "search", required = false) String search) {
        AuthUser user = authService.getAuthUser();
        return this.logPublicEventsService.getListEvents(user, pageable, search);
    }

    @PreAuthorize("hasAuthority('CAN_LOG_READ')")
    @GetMapping(value = "/private")
    @ApiOperation(value = "Getting list of document public events for the authenticated user", response = Page.class)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "pageable",
                    required = true,
                    dataType = "Pageable",
                    value = "Page parameters"
            ),
            @ApiImplicitParam(
                    name = "search",
                    paramType = "request",
                    dataType = "String",
                    value = "Additional search criteria"
            )
    })
    public Page<LogPrivateEvents> findAllPrivateEvents(Pageable pageable,
                                                       @RequestParam(value = "search", required = false) String search) {
        AuthUser user = authService.getAuthUser();
        return this.logPrivateEventsService.getListEvents(user, pageable, search);
    }
}
