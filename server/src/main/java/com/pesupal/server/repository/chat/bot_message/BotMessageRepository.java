package com.pesupal.server.repository.chat.bot_message;

import com.pesupal.server.model.chat.bot_message.BotMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotMessageRepository extends JpaRepository<BotMessage, Long> {
}
