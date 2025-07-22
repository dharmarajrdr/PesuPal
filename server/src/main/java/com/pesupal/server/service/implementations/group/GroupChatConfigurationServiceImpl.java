package com.pesupal.server.service.implementations.group;

import com.pesupal.server.repository.GroupChatConfigurationRepository;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatConfigurationServiceImpl implements GroupChatConfigurationService {

    private final GroupChatConfigurationRepository groupChatConfigurationRepository;
}
