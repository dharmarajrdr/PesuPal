package com.pesupal.server.service.implementations.chat.direct_message;

import com.pesupal.server.model.chat.DirectMessageMediaFile;
import com.pesupal.server.repository.chat.direct_message.DirectMessageMediaFileRepository;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageMediaFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectMessageMediaFileServiceImpl implements DirectMessageMediaFileService {

    private final DirectMessageMediaFileRepository directMessageMediaFileRepository;

    /**
     * Saves a DirectMessageMediaFile entity.
     *
     * @param directMessageMediaFile
     * @return
     */
    @Override
    public DirectMessageMediaFile save(DirectMessageMediaFile directMessageMediaFile) {

        return directMessageMediaFileRepository.save(directMessageMediaFile);
    }
}
