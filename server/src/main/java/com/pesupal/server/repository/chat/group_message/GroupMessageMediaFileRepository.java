package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupMessageMediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMessageMediaFileRepository extends JpaRepository<GroupMessageMediaFile, Long> {

    GroupMessageMediaFile findByGroupChatMessage(GroupChatMessage gm);
}
