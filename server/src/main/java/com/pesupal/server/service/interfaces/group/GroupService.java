package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.ChatPreviewDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.model.group.Group;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    GroupDto createGroup(CreateGroupDto createGroupDto);

    Group getGroupByPublicId(String groupId);

    void deleteGroup(String groupId);

    RecentChatPagedDto getAllGroups(Pageable pageable);

    ChatPreviewDto getGroupChatPreviewByChatId(String groupId);
}
