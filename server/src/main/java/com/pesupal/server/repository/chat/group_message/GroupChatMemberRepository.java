package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.group_message.GroupChatMember;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatMemberRepository extends JpaRepository<GroupChatMember, Long> {

    Optional<GroupChatMember> findByGroupIdAndParticipantId(Long groupId, Long participantId);

    boolean existsByGroup_PublicIdAndParticipant_PublicId(String groupId, String participantId);

    Optional<GroupChatMember> findByGroup_PublicIdAndParticipantId(String groupId, Long userId);

    List<GroupChatMember> findAllByGroup_PublicId(String groupId);

    @Modifying
    @Query("UPDATE GroupChatMember gcm SET gcm.lastReadMessage = NULL WHERE gcm.lastReadMessage = :message")
    void updateLastReadMessageToNull(@Param("message") GroupChatMessage message);
}
