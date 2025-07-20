package com.pesupal.server.repository;

import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.chat.DirectMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {

    Page<DirectMessage> findByChatId(String chatId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE DirectMessage dm SET dm.readReceipt = :readReceipt WHERE dm.chatId = :chatId AND dm.receiver.id = :receiverId AND dm.readReceipt <> :readReceipt")
    void markMessagesAsRead(@Param("chatId") String chatId, @Param("receiverId") Long receiverId, @Param("readReceipt") ReadReceipt readReceipt);

    @Query(value = """
             SELECT
                om.display_picture AS displayPicture,
                om.display_name AS displayName,
                om.status AS userStatus,
            
                CASE
                    WHEN dm.sender_id = :userId THEN 'Me'
                    ELSE sender_member.display_name
                END AS senderName,
            
                dm.message AS content,
                dm.contains_media AS includedMedia,
                dm.created_at AS createdAt,
                dm.read_receipt AS readReceipt,
                dm.chat_id AS chatId
            
            FROM direct_message dm
            
            -- Identify the "other" participant in the chat
            JOIN org_member om ON om.user_id = CASE
                WHEN dm.sender_id = :userId THEN dm.receiver_id
                ELSE dm.sender_id
            END AND om.org_id = :orgId
            
            -- Identify the "sender" name (used in CASE above)
            JOIN org_member sender_member ON sender_member.user_id = dm.sender_id AND sender_member.org_id = :orgId
            
            -- Get only the latest message from each chat
            JOIN (
                SELECT chat_id, MAX(created_at) AS latest
                FROM direct_message
                WHERE org_id = :orgId
                  AND (sender_id = :userId OR receiver_id = :userId)
                GROUP BY chat_id
            ) latest_msg ON dm.chat_id = latest_msg.chat_id AND dm.created_at = latest_msg.latest
            
            WHERE dm.org_id = :orgId
              AND (dm.sender_id = :userId OR dm.receiver_id = :userId)
            
            ORDER BY dm.created_at DESC
            LIMIT :limit OFFSET :offset;
            """, nativeQuery = true)
    List<Object[]> findRecentChatsPaged(@Param("userId") Long userId, @Param("orgId") Long orgId, @Param("limit") int limit, @Param("offset") int offset);


    @Query(value = """
            SELECT COUNT(*)
            FROM (
                SELECT chat_id
                FROM direct_message
                WHERE org_id = :orgId
                  AND (sender_id = :userId OR receiver_id = :userId)
                GROUP BY chat_id
            ) AS chat_count
            """, nativeQuery = true)
    Long countRecentChats(@Param("userId") Long userId, @Param("orgId") Long orgId);

}
