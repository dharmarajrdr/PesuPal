package com.pesupal.server.controller.group;

import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-configuration")
public class GroupChatConfigurationController extends CurrentValueRetriever {

    private final GroupChatConfigurationService groupChatConfigurationService;
}
