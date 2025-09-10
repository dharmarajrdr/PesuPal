package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface PostTagService {

    Page<PostTag> findAllByTagAndOrgId(String tagName, Long orgId, Pageable pageable);

    List<PostTag> saveAll(Set<String> tags, Post post);

    List<PostTag> updateTags(Post post, Set<String> tags);
}
