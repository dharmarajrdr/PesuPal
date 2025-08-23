package com.pesupal.server.service.implementations.post;

import com.pesupal.server.model.post.Post;
import com.pesupal.server.repository.post.PostMediaRepository;
import com.pesupal.server.service.interfaces.post.PostMediaService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
