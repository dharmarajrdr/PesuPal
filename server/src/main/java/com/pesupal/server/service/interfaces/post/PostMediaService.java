package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.response.MediaDto;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostMedia;

import java.util.List;
import java.util.Set;

public interface PostMediaService {

    void unlinkMediaFromPost(Post post);

    List<PostMedia> saveAll(Set<MediaDto> mediaIds, Post post);
}
