package com.pesupal.server.service.implementations.group;

import com.pesupal.server.repository.GroupChatReactionRepository;
import com.pesupal.server.service.interfaces.group.GroupChatReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatReactionServiceImpl implements GroupChatReactionService {

    private final GroupChatReactionRepository groupChatReactionRepository;
}
