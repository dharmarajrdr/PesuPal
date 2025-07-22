package com.pesupal.server.service.implementations.group;

import com.pesupal.server.repository.GroupMessagePinnedRepository;
import com.pesupal.server.service.interfaces.group.GroupMessagePinnedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupMessagePinnedServiceImpl implements GroupMessagePinnedService {

    private final GroupMessagePinnedRepository groupMessagePinnedRepository;
}
