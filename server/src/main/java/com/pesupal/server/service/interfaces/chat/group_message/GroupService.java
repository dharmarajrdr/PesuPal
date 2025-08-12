package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.CreateGroupDto;
import com.pesupal.server.dto.response.chat.ChatPreviewDto;
import com.pesupal.server.dto.response.chat.RecentChatPagedDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.model.chat.group_message.Group;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    GroupDto createGroup(CreateGroupDto createGroupDto);

    Group getGroupByPublicId(String groupId);

    void deleteGroup(String groupId);

    RecentChatPagedDto getAllGroups(Pageable pageable);

    ChatPreviewDto getGroupChatPreviewByChatId(String groupId);

    void reopenGroup(String groupId);

    GroupDto updateGroup(String groupId, CreateGroupDto createGroupDto);
}
