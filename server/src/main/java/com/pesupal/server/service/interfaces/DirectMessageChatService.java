package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.chat.DirectMessageChat;

public interface DirectMessageChatService {

    DirectMessageChat getDirectMessageChat(String orgMemberPublicId1, String orgMemberPublicId2);

    DirectMessageChat getDirectMessageByPublicId(String chatId);
}
