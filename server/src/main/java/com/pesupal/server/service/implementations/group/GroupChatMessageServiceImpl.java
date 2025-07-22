package com.pesupal.server.service.implementations.group;

import com.pesupal.server.repository.GroupChatMessageRepository;
import com.pesupal.server.service.interfaces.group.GroupChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatMessageServiceImpl implements GroupChatMessageService {

    private final GroupChatMessageRepository groupChatMessageRepository;
}
