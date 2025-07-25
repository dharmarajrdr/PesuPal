package com.pesupal.server.service.implementations;

import com.pesupal.server.model.chat.DirectMessageMediaFile;
import com.pesupal.server.repository.DirectMessageMediaFileRepository;
import com.pesupal.server.service.interfaces.DirectMessageMediaFileService;
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
