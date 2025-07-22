package com.pesupal.server.service.implementations.group;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatMemberServiceImpl implements GroupChatMemberService {

    private final GroupChatMemberRepository groupChatMemberRepository;

    /**
     * Initializes the group chat member for a given group and organization member.
     *
     * @param group
     * @param orgMember
     */
    @Override
    public void initializeGroupChatMember(Group group, OrgMember orgMember) {

        GroupChatMember groupChatMember = new GroupChatMember();
        groupChatMember.setGroup(group);
        groupChatMember.setUser(orgMember.getUser());
        groupChatMember.setActive(true);
        groupChatMember.setRole(Role.SUPER_ADMIN);
        groupChatMemberRepository.save(groupChatMember);
    }
}
