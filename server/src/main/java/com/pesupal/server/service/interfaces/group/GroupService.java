package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.model.group.Group;

public interface GroupService {

    GroupDto createGroup(CreateGroupDto createGroupDto, Long userId, Long orgId);

    Group getGroupById(Long groupId);
}
