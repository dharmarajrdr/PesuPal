package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.model.group.Group;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    GroupDto createGroup(CreateGroupDto createGroupDto, Long userId, Long orgId);

    Group getGroupById(Long groupId);

    void deleteGroup(Long groupId, Long userId, Long orgId);

    RecentChatPagedDto getAllGroups(Long userId, Long orgId, Pageable pageable);
}
