package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.request.post.CreatePostMentionsDto;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostMention;

import java.util.List;

public interface PostMentionService {

    List<PostMention> saveAll(CreatePostMentionsDto mentions, Post post);
}
