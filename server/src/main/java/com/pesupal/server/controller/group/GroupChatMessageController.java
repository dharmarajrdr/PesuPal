package com.pesupal.server.controller.group;

import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-messages")
public class GroupChatMessageController extends CurrentValueRetriever {

    private final GroupChatMessageService groupChatMessageService;
}
