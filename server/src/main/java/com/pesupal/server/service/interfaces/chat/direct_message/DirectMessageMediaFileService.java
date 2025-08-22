package com.pesupal.server.service.interfaces.chat.direct_message;

import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.direct_message.DirectMessageMediaFile;

public interface DirectMessageMediaFileService {

    DirectMessageMediaFile save(DirectMessageMediaFile directMessageMediaFile);

    void unlinkMediaFilesByDirectMessage(DirectMessage directMessage);
}
