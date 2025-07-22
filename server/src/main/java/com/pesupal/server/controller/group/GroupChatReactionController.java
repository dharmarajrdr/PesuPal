package com.pesupal.server.controller.group;

import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.group.GroupChatReactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/group-chat-reactions")
public class GroupChatReactionController extends CurrentValueRetriever {

    private final GroupChatReactionService groupChatReactionService;
}
