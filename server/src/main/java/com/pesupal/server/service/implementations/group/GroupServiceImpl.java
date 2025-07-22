package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.repository.GroupRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.group.GroupService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final OrgMemberService orgMemberService;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatConfigurationService groupChatConfigurationService;

    /**
     * Initializes the group chat member for a given group and organization member.
     *
     * @param group
     * @param orgMember
     */
    private void initializeGroupChatMember(Group group, OrgMember orgMember) {

        GroupChatMember groupChatMember = new GroupChatMember();
        groupChatMember.setGroup(group);
        groupChatMember.setUser(orgMember.getUser());
        groupChatMember.setActive(true);
        groupChatMember.setRole(Role.SUPER_ADMIN);
        groupChatMemberRepository.save(groupChatMember);
    }

    /**
     * Creates a new group based on the provided CreateGroupDto.
     *
     * @param createGroupDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    @Transactional
    public GroupDto createGroup(CreateGroupDto createGroupDto, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Group group = createGroupDto.toGroup();
        group.setOwner(orgMember.getUser());
        group.setOrg(orgMember.getOrg());
        groupRepository.save(group);
        groupChatConfigurationService.initializeGroupChatConfiguration(group);
        initializeGroupChatMember(group, orgMember);
        return GroupDto.fromGroupAndOrgMember(group, orgMember);
    }

    /**
     * Retrieves a group by its ID and organization ID.
     *
     * @param groupId
     * @return
     */
    @Override
    public Group getGroupById(Long groupId) {

        return groupRepository.findById(groupId).orElseThrow(() -> new DataNotFoundException("Group with ID " + groupId + " not found."));
    }
}
