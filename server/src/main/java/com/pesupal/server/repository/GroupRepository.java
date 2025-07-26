package com.pesupal.server.repository;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.org.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = """
            SELECT COUNT(*)
            FROM (
                SELECT g.id AS group_id
                FROM group_chat_member gcm
                JOIN groups g ON g.id = gcm.group_id AND g.org_id = :orgId
                JOIN group_chat_message msg ON msg.group_id = g.id AND msg.deleted = false
            
                WHERE gcm.user_id = :userId
                  AND gcm.active = true
                  AND g.active = true
            
                GROUP BY g.id
            ) AS group_chat_count
            """, nativeQuery = true)
    Long countRecentGroupChats(@Param("userId") Long userId, @Param("orgId") Long orgId);

    @Query(value = """
                SELECT
                    g.id AS groupId,
                    g.name AS groupName,
                    g.visibility AS groupVisibility,
                    g.display_picture AS senderDisplayPicture,
            
                    sender.display_name AS senderName,
            
                    msg.message AS content,
                    msg.contains_media AS includedMedia,
                    msg.created_at AS createdAt
            
                FROM group_chat_member gcm
                JOIN groups g ON g.id = gcm.group_id AND g.org_id = :orgId
                JOIN group_chat_message msg ON msg.group_id = g.id
                JOIN org_member sender ON sender.user_id = msg.sender_id AND sender.org_id = :orgId
            
                -- Get only the latest message for each group
                JOIN (
                    SELECT group_id, MAX(created_at) AS latest
                    FROM group_chat_message
                    WHERE deleted = false
                    GROUP BY group_id
                ) latest_msg ON latest_msg.group_id = msg.group_id AND latest_msg.latest = msg.created_at
            
                WHERE gcm.user_id = :userId
                  AND gcm.active = true
                  AND g.active = true
            
                ORDER BY msg.created_at DESC
                LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Object[]> findRecentGroupChatsPaged(@Param("userId") Long userId, @Param("orgId") Long orgId, @Param("limit") int limit, @Param("offset") int offset);


    Optional<Group> findByPublicIdAndOrg(String groupPublicId, Org org);
}
