package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupChatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupChatMemberRepository extends JpaRepository<GroupChatMember, Long> {

    Optional<GroupChatMember> findByGroupIdAndParticipantId(Long groupId, Long participantId);

    boolean existsByGroup_PublicIdAndParticipant_PublicId(String groupId, String participantId);

    Optional<GroupChatMember> findByGroup_PublicIdAndParticipantId(String groupId, Long userId);
}
