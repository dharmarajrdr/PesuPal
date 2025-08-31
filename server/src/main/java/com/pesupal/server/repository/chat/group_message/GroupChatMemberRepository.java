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

    Optional<GroupChatMember> findByGroup_PublicIdAndParticipantId(String groupId, Long userId);

    @Modifying
    @Query("UPDATE GroupChatMember gcm SET gcm.lastReadMessage = NULL WHERE gcm.lastReadMessage = :message")
    void updateLastReadMessageToNull(@Param("message") GroupChatMessage message);

    @Modifying
    @Query("UPDATE GroupChatMember m SET m.lastReadMessage = NULL WHERE m.group = :group")
    void updateAllLastReadMessageToNullByGroup(@Param("group") Group group);

    @Query("""
                    SELECT om
                    FROM OrgMember om
                    WHERE (
                        LOWER(om.displayName) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(om.userName) LIKE LOWER(CONCAT('%', :search, '%'))
                    )
                    AND om.org.id = :orgId
                    AND om.id NOT IN (
                        SELECT gm.participant.id
                        FROM GroupChatMember gm
                        WHERE gm.group.publicId = :groupId
                        AND gm.active = true
                    )
                    AND om.archived = false
                    ORDER BY om.displayName ASC
            """)
    Page<OrgMember> getNonParticipantMembersByGroupId(@Param("groupId") String groupId, @Param("orgId") Long orgId, @Param("search") String search, Pageable pageable);

    List<GroupChatMember> findAllByGroup_PublicIdAndActive(String groupId, boolean active);
}
