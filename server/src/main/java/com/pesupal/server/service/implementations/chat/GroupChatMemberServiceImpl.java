package com.pesupal.server.service.implementations.chat;

import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.service.interfaces.chat.GroupChatMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatMemberServiceImpl implements GroupChatMemberService {

    private final GroupChatMemberRepository groupChatMemberRepository;
}
