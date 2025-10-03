package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.MessageStatus;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupMessageMediaFile;
import com.pesupal.server.model.org.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMessageMediaFileRepository extends JpaRepository<GroupMessageMediaFile, Long> {

    Optional<GroupMessageMediaFile> findByGroupChatMessage(GroupChatMessage gm);

    List<GroupMessageMediaFile> findAllByGroupChatMessageIsNull();

    List<GroupMessageMediaFile> findAllByGroupChatMessage_GroupAndGroupChatMessage_MessageStatusIn(Group group, List<MessageStatus> sent);

    List<GroupMessageMediaFile> findAllByGroupChatMessage_Group_Org(Org groupChatMessageGroupOrg);
}
