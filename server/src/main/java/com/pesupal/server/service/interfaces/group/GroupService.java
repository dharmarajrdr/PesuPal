package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.ChatPreviewDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.model.group.Group;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    GroupDto createGroup(CreateGroupDto createGroupDto);

    Group getGroupById(Long groupId);

    void deleteGroup(String groupPublicId);

    RecentChatPagedDto getAllGroups(Long userId, Long orgId, Pageable pageable);

    ChatPreviewDto getGroupChatPreviewByChatId(Long groupId, Long userId, Long orgId);
}
