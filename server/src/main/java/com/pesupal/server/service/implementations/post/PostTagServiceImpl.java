package com.pesupal.server.service.implementations.post;

import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostTag;
import com.pesupal.server.repository.post.PostTagRepository;
import com.pesupal.server.service.interfaces.post.PostTagService;
import com.pesupal.server.service.interfaces.post.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostTagServiceImpl implements PostTagService {

    private final TagService tagService;
    private final PostTagRepository postTagRepository;

    /**
     * @param tagName
     * @return
     */
    @Override
    public Page<PostTag> findAllByTagAndOrgId(String tagName, Long orgId, Pageable pageable) {

        return postTagRepository.findAllByTag_NameAndPost_Org_IdAndPost_StatusOrderByPost_CreatedAtDesc(tagName, orgId, PostStatus.PUBLISHED, pageable);
    }

    private Set<String> sanitizeTags(Set<String> tags) {
        return tags.stream().map(tag -> {
            if (!tag.startsWith("#")) {
                return "#" + tag;
            }
            return tag;
        }).collect(Collectors.toSet());
    }

    /**
     * Saves all tags associated with a post.
     *
     * @param tags
     * @param post
     */
    @Override
    @Transactional
    public List<PostTag> saveAll(Set<String> tags, Post post) {

        if (tags.isEmpty()) {
            return new ArrayList<>();
        }

        tags = sanitizeTags(tags);

        List<PostTag> postTags = tags.stream().map(tagName -> {
            PostTag postTag = new PostTag();
            postTag.setTag(tagService.createOrGet(tagName));
            postTag.setPost(post);
            return postTag;
        }).toList();
        return postTagRepository.saveAll(postTags);
    }

    /**
     * Updates the tags associated with a post.
     *
     * @param post
     * @param tags
     */
    @Override
    @Transactional
    public List<PostTag> updateTags(Post post, Set<String> tags) {

        tags = sanitizeTags(tags);

        List<PostTag> existingTags = post.getTags(); // managed persistent collection

        // 1. Remove old tags
        Set<String> finalTags = tags;
        existingTags.removeIf(et -> !finalTags.contains(et.getTag().getName()));

        // 2. Add new tags
        for (String tag : tags) {
            boolean exists = existingTags.stream()
                    .anyMatch(et -> et.getTag().getName().equals(tag));
            if (!exists) {
                PostTag postTag = new PostTag();
                postTag.setTag(tagService.createOrGet(tag));
                postTag.setPost(post);
                existingTags.add(postTag); // ✅ add to persistent collection
            }
        }

        return existingTags; // same collection reference, no problem
    }

}
