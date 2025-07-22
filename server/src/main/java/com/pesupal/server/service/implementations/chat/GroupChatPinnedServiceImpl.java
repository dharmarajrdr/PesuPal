package com.pesupal.server.service.implementations.chat;

import com.pesupal.server.repository.GroupChatPinnedRepository;
import com.pesupal.server.service.interfaces.chat.GroupChatPinnedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatPinnedServiceImpl implements GroupChatPinnedService {

    private final GroupChatPinnedRepository groupChatPinnedRepository;
}
