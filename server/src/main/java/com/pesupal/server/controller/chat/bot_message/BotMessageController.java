package com.pesupal.server.controller.chat.bot_message;

import com.pesupal.server.service.interfaces.chat.bot_message.BotMessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/chat/bot-messages")
public class BotMessageController {

    private final BotMessageService botMessageService;
}
