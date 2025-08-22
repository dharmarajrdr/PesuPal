package com.pesupal.server.service.implementations.chat.bot_message;

import com.pesupal.server.repository.chat.bot_message.BotMessageRepository;
import com.pesupal.server.service.interfaces.chat.bot_message.BotMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotMessageServiceImpl implements BotMessageService {

    private final BotMessageRepository botMessageRepository;
}
