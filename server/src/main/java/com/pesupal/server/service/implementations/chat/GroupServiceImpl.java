package com.pesupal.server.service.implementations.chat;

import com.pesupal.server.repository.GroupRepository;
import com.pesupal.server.service.interfaces.chat.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
}
