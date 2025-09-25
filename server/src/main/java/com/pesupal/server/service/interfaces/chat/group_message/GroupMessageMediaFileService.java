package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.org.Org;

public interface GroupMessageMediaFileService {

    void unlinkMediaFilesByGroupMessage(GroupChatMessage groupChatMessage);

    void unlinkAllMediaFilesByGroup(Group group);

    void deleteAllByOrg(Org deletedOrg);
}
