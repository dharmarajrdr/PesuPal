package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMember;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Modifying
    @Query("UPDATE GroupChatMember m SET m.lastReadMessage = NULL WHERE m.group = :group")
    void updateAllLastReadMessageToNullByGroup(@Param("group") Group group);

    @Query("""
                    SELECT u
                    FROM OrgMember u
                    WHERE (
                        LOWER(u.displayName) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%'))
                    )
                    AND u.id NOT IN (
                        SELECT gm.participant.id
                        FROM GroupChatMember gm
                        WHERE gm.group.publicId = :groupId
                    )
                    ORDER BY u.displayName ASC
            """)
    Page<OrgMember> getNonParticipantMembersByGroupId(@Param("groupId") String groupId, @Param("search") String search, Pageable pageable);
}
