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

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {

    Page<DirectMessage> findByChatId(String chatId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE DirectMessage dm SET dm.readReceipt = :readReceipt WHERE dm.chatId = :chatId AND dm.receiver.id = :receiverId AND dm.readReceipt <> :readReceipt")
    void markMessagesAsRead(@Param("chatId") String chatId, @Param("receiverId") Long receiverId, @Param("readReceipt") ReadReceipt readReceipt);

}
