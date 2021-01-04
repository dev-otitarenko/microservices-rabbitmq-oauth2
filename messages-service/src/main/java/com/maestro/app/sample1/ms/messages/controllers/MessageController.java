package com.maestro.app.sample1.ms.messages.controllers;

import com.maestro.app.sample1.ms.messages.entities.UserMessages;
import com.maestro.app.sample1.ms.messages.records.DeletePrm;
import com.maestro.app.sample1.ms.messages.services.UserMessagesService;
import com.maestro.app.sample1.ms.messages.services.auth.IAuthenticationFacade;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.exceptions.EntityRecordNotFound;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Operators with messages")
@RestController
public class MessageController {
    private final UserMessagesService messageService;
    private final IAuthenticationFacade authService;

    public MessageController(UserMessagesService messageService,
                             IAuthenticationFacade authService) {
        this.messageService = messageService;
        this.authService = authService;
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "Getting list of messages for the authenticated user", response = List.class)
    public List<UserMessages> getUserMessages() {
        AuthUser user = authService.getAuthUser();
        return messageService.getListMessages(user.getId());
    }

    @GetMapping(value = "/count")
    @ApiOperation(value = "Getting count of connect events in queue for the authenticated user", response = Page.class)
    public long countUserMessages() {
        AuthUser user = authService.getAuthUser();
        return messageService.getCountMessages(user.getId());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Removing a particular message for the authenticated user")
    public void deleteUserMessage(@ApiParam(value = "Message ID", required = true)  @PathVariable String id) throws EntityRecordNotFound {
        messageService.deleteMessage(authService.getAuthUser(), id);
    }

    @PostMapping(value = "/clear")
    @ApiOperation(value = "Removing messages specified by list of id messages")
    public void deleteUserMessages(@ApiParam(value = "List of messages IDs", required = true) @RequestBody DeletePrm prm) throws EntityRecordNotFound {
        messageService.deleteMessages(authService.getAuthUser(), prm.getIds());
    }

    @PostMapping(value = "/clear-all")
    @ApiOperation(value = "Removing all messages")
    public void deleteUserMessages() {
        messageService.deleteAllMessages(authService.getAuthUser());
    }
}
