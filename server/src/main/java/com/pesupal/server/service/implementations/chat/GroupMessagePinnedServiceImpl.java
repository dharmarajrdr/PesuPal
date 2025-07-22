package com.pesupal.server.service.implementations.chat;

import com.pesupal.server.repository.GroupMessagePinnedRepository;
import com.pesupal.server.service.interfaces.chat.GroupMessagePinnedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupMessagePinnedServiceImpl implements GroupMessagePinnedService {

    private final GroupMessagePinnedRepository groupMessagePinnedRepository;
}
