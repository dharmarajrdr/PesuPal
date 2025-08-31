package com.pesupal.server.service.implementations.chat.direct_message;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.direct_message.DirectMessageMediaFile;
import com.pesupal.server.repository.chat.direct_message.DirectMessageMediaFileRepository;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageMediaFileService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DirectMessageMediaFileServiceImpl implements DirectMessageMediaFileService {

    private final S3Service s3Service;
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

    /**
     * Unlinks media file associated with a given direct message.
     *
     * @param directMessage
     */
    @Override
    public void unlinkMediaFilesByDirectMessage(DirectMessage directMessage) {

        if (!directMessage.getContainsMedia()) {
            return;
        }

        Optional<DirectMessageMediaFile> optionalDirectMessageMediaFile = directMessageMediaFileRepository.findByDirectMessage(directMessage);
        if (optionalDirectMessageMediaFile.isEmpty()) {
            throw new DataNotFoundException("No media file associated with this message.");
        }
        DirectMessageMediaFile directMessageMediaFile = optionalDirectMessageMediaFile.get();
        directMessageMediaFile.setDirectMessage(null);
        directMessageMediaFileRepository.save(directMessageMediaFile);
    }

    /**
     * Performs garbage collection on media files that are no longer associated with any direct messages.
     * This method removes orphaned media files from the repository and s3 storage.
     */
    @Scheduled(cron = "${aws.s3.garbage-collection.cron}")
    public void garbageCollect() {

        List<DirectMessageMediaFile> directMessageMediaFiles = directMessageMediaFileRepository.findAllByDirectMessageIsNull();
        for (DirectMessageMediaFile directMessageMediaFile : directMessageMediaFiles) {
            s3Service.deleteFile(directMessageMediaFile.getMediaId());
        }
        directMessageMediaFileRepository.deleteAll(directMessageMediaFiles);
    }
}
