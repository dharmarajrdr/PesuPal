package com.pesupal.server.repository;

import com.pesupal.server.model.chat.DirectMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {

    Page<DirectMessage> findByChatIdAndOrgId(String chatId, Long orgId, Pageable pageable);
}
