package com.pesupal.server.service.implementations.group;

import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatMemberServiceImpl implements GroupChatMemberService {

    private final GroupChatMemberRepository groupChatMemberRepository;
}
