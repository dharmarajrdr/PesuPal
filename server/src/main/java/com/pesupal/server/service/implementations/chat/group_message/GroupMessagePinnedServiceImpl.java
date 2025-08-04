package com.pesupal.server.service.implementations.chat.group_message;

import com.pesupal.server.repository.GroupMessagePinnedRepository;
import com.pesupal.server.service.interfaces.chat.group_message.GroupMessagePinnedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupMessagePinnedServiceImpl implements GroupMessagePinnedService {

    private final GroupMessagePinnedRepository groupMessagePinnedRepository;
}
