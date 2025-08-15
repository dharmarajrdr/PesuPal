package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.MessageStatus;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long> {

    void deleteAllByGroup(Group group);

    Page<GroupChatMessage> findAllByGroup_PublicIdAndIdLessThanAndMessageStatusIn(String groupId, Long pivotMessageId, List<MessageStatus> messageStatuses, Pageable pageable);

    Page<GroupChatMessage> findAllByGroup_PublicIdAndMessageStatusIn(String groupId, List<MessageStatus> messageStatuses, Pageable pageable);

    Optional<GroupChatMessage> findFirstByGroupOrderByCreatedAtDesc(Group group);

    List<GroupChatMessage> findAllByGroup_PublicIdAndMessageStatus(String chatId, MessageStatus messageStatus);

    int countByGroup_PublicIdAndMessageStatus(String chatId, MessageStatus messageStatus);

    List<GroupChatMessage> findAllByGroupAndSenderAndMessageStatus(Group group, OrgMember orgMember, MessageStatus messageStatus);
}
