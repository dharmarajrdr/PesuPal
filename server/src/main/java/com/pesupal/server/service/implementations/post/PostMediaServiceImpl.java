package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.response.MediaDto;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostMedia;
import com.pesupal.server.repository.post.PostMediaRepository;
import com.pesupal.server.service.interfaces.post.PostMediaService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostMediaServiceImpl implements PostMediaService {

    private final S3Service s3Service;
    private final PostMediaRepository postMediaRepository;

    /**
     * Deletes a single post media.
     *
     * @param postMedia
     */
    private void deletePostMedia(PostMedia postMedia) {

        s3Service.deleteFile(postMedia.getMediaId());
        postMedia.setPost(null);
        postMediaRepository.save(postMedia);
    }

    /**
     * Unlink media from a post.
     *
     * @param post
     */
    @Override
    public void unlinkMediaFromPost(Post post) {

        post.getPostMedia().forEach(this::deletePostMedia);
    }

    /**
     * Saves all media associated with a post.
     *
     * @param mediaIds
     * @param post
     * @return
     */
    @Override
    @Transactional
    public List<PostMedia> saveAll(Set<MediaDto> mediaIds, Post post) {

        if (mediaIds == null || mediaIds.isEmpty()) {
            return List.of();
        }

        return mediaIds.stream().map(mediaId -> {
            PostMedia postMedia = PostMedia.builder().post(post).mediaId(mediaId.getId()).build();
            return postMediaRepository.save(postMedia);
        }).collect(Collectors.toList());
    }

    /**
     * Updates media associated with a post.
     *
     * @param post
     * @param mediaIds
     * @return
     */
    @Override
    @Transactional
    public List<PostMedia> updatePostMedia(Post post, Set<MediaDto> mediaIds) {

        if (mediaIds == null) {
            return post.getPostMedia();
        }

        List<PostMedia> existingMedia = post.getPostMedia(); // managed collection

        // 1. Remove old media
        existingMedia.removeIf(pm -> mediaIds.stream().noneMatch(dto -> dto.getId().equals(pm.getMediaId())));

        // 2. Add new media
        for (MediaDto dto : mediaIds) {
            boolean exists = existingMedia.stream().anyMatch(pm -> pm.getMediaId().equals(dto.getId()));
            if (!exists) {
                PostMedia newMedia = PostMedia.builder().post(post).mediaId(dto.getId()).build();
                existingMedia.add(newMedia); // cascade handles persist
            }
        }

        return existingMedia;
    }
}
