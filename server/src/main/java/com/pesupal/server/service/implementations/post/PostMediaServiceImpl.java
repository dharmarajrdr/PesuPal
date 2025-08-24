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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostMediaServiceImpl implements PostMediaService {

    private final S3Service s3Service;
    private final PostMediaRepository postMediaRepository;

    /**
     * Unlink media from a post.
     *
     * @param post
     */
    @Override
    public void unlinkMediaFromPost(Post post) {
        post.getPostMedia().forEach(postMedia -> {
            UUID mediaId = postMedia.getMediaId();
            String extension = postMedia.getExtension();
            String key = mediaId + "." + extension;
            s3Service.deleteFile(key);
            postMedia.setPost(null);
            postMediaRepository.save(postMedia);
        });
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

        if (mediaIds.isEmpty()) {
            return List.of();
        }

        return mediaIds.stream().map(mediaId -> {
            PostMedia postMedia = PostMedia.builder().post(post).mediaId(mediaId.getId()).extension(mediaId.getExtension()).build();
            return postMediaRepository.save(postMedia);
        }).collect(Collectors.toList());
    }
}
