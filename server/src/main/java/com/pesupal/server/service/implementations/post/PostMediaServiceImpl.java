package com.pesupal.server.service.implementations.post;

import com.pesupal.server.model.post.Post;
import com.pesupal.server.repository.post.PostMediaRepository;
import com.pesupal.server.service.interfaces.post.PostMediaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostMediaServiceImpl implements PostMediaService {

    private final PostMediaRepository postMediaRepository;

    /**
     * Unlink media from a post.
     *
     * @param post
     */
    @Override
    public void unlinkMediaFromPost(Post post) {
        post.getPostMedia().forEach(postMedia -> {
            postMedia.setPost(null);
            postMediaRepository.save(postMedia);
        });
    }
}
