package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupMessageMediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMessageMediaFileRepository extends JpaRepository<GroupMessageMediaFile, Long> {

    Optional<GroupMessageMediaFile> findByGroupChatMessage(GroupChatMessage gm);

    List<GroupMessageMediaFile> findAllByGroupChatMessage_Group(Group groupChatMessageGroup);

    List<GroupMessageMediaFile> findAllByGroupChatMessageIsNull();
}
