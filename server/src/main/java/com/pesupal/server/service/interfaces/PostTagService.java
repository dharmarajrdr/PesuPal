package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.post.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostTagService {

    Page<PostTag> findAllByTagAndOrgId(String tagName, Long orgId, Pageable pageable);
}
