package com.pesupal.server.service.implementations;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.post.PostTag;
import com.pesupal.server.repository.PostTagRepository;
import com.pesupal.server.service.interfaces.PostTagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostTagServiceImpl implements PostTagService {

    private final PostTagRepository postTagRepository;

    /**
     * @param tagName
     * @return
     */
    @Override
    public Page<PostTag> findAllByTagAndOrgId(String tagName, Long orgId, Pageable pageable) {

        return postTagRepository.findAllByTag_NameAndPost_Org_IdAndPost_StatusOrderByPost_CreatedAtDesc(tagName, orgId, PostStatus.PUBLISHED, pageable);
    }
}
