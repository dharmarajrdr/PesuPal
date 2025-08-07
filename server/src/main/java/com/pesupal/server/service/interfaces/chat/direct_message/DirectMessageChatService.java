package com.pesupal.server.service.interfaces.chat.direct_message;

import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.user.OrgMember;

public interface DirectMessageChatService {

    DirectMessageChat getDirectMessageChat(String orgMemberPublicId1, String orgMemberPublicId2);

    DirectMessageChat getOrCreateDirectMessageChat(OrgMember user1, OrgMember user2);

    DirectMessageChat getDirectMessageByPublicId(String chatId);
}
