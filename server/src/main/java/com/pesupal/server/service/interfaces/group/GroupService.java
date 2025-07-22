package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.group.GroupDto;

public interface GroupService {

    GroupDto createGroup(CreateGroupDto createGroupDto, Long userId, Long orgId);
}
