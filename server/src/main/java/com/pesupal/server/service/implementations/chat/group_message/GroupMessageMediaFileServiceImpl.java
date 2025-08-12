package com.pesupal.server.service.implementations.chat.group_message;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupMessageMediaFile;
import com.pesupal.server.repository.chat.group_message.GroupMessageMediaFileRepository;
import com.pesupal.server.service.interfaces.chat.group_message.GroupMessageMediaFileService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupMessageMediaFileServiceImpl implements GroupMessageMediaFileService {

    private final S3Service s3Service;
    private final GroupMessageMediaFileRepository groupMessageMediaFileRepository;


    /**
     * Unlinks media file associated with a given group message.
     *
     * @param groupChatMessage
     */
    @Override
    public void unlinkMediaFilesByGroupMessage(GroupChatMessage groupChatMessage) {

        if (!groupChatMessage.isContainsMedia()) {
            return;
        }

        Optional<GroupMessageMediaFile> optionalGroupMessageMediaFile = groupMessageMediaFileRepository.findByGroupChatMessage(groupChatMessage);
        if (optionalGroupMessageMediaFile.isEmpty()) {
            throw new DataNotFoundException("No media file associated with this message.");
        }
        GroupMessageMediaFile groupMessageMediaFile = optionalGroupMessageMediaFile.get();
        groupMessageMediaFile.setGroupChatMessage(null);
        groupMessageMediaFileRepository.save(groupMessageMediaFile);
    }

    /**
     * Unlinks all media files associated with a given group.
     *
     * @param group
     */
    @Override
    public void unlinkAllMediaFilesByGroup(Group group) {

        List<GroupMessageMediaFile> groupMessageMediaFiles = groupMessageMediaFileRepository.findAllByGroupChatMessage_Group(group);
        for (GroupMessageMediaFile groupMessageMediaFile : groupMessageMediaFiles) {
            groupMessageMediaFile.setGroupChatMessage(null);
        }
        groupMessageMediaFileRepository.saveAll(groupMessageMediaFiles);
    }

    /**
     * Performs garbage collection on media files that are no longer associated with any group messages.
     * This method removes orphaned media files from the repository and s3 storage.
     */
    @Scheduled(cron = "${aws.s3.garbage-collection.cron}")
    public void garbageCollect() {

        List<GroupMessageMediaFile> groupMessageMediaFiles = groupMessageMediaFileRepository.findAllByGroupChatMessageIsNull();
        for (GroupMessageMediaFile groupMessageMediaFile : groupMessageMediaFiles) {
            UUID mediaId = groupMessageMediaFile.getMediaId();
            String extension = groupMessageMediaFile.getExtension();
            String key = mediaId + "." + extension;
            s3Service.deleteFile(key);
        }
        groupMessageMediaFileRepository.deleteAll(groupMessageMediaFiles);
    }
}
