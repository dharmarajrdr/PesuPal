package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.request.post.CreatePostMentionsDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostMention;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.post.PostMentionRepository;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.service.interfaces.post.PostMentionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PostMentionServiceImpl extends CurrentValueRetriever implements PostMentionService {

    private final OrgMemberService orgMemberService;
    private final PostMentionRepository postMentionRepository;

    /**
     * Saves all post mentions.
     *
     * @param mentions
     * @param post
     * @return
     */
    @Override
    @Transactional
    public List<PostMention> saveAll(CreatePostMentionsDto mentions, Post post) {

        if (mentions == null) {
            return List.of();
        }

        OrgMember postOwner = getCurrentOrgMember();

        List<PostMention> postMentions = mentions.getData().stream().map(memberId -> {
            PostMention postMention = new PostMention();
            postMention.setPost(post);
            OrgMember mentionedMember = orgMemberService.getOrgMemberByPublicId(memberId);
            if (mentionedMember.getPublicId().equals(postOwner.getPublicId())) {
                throw new ActionProhibitedException("You cannot mention yourself in a post.");
            }
            postMention.setMentionedMember(mentionedMember);
            return postMention;
        }).toList();

        return postMentionRepository.saveAll(postMentions);
    }

    /**
     * Updates post mentions.
     *
     * @param post
     * @param mentions
     * @return
     */
    @Override
    @Transactional
    public List<PostMention> updateMentions(Post post, CreatePostMentionsDto mentions) {

        if (mentions == null) {
            return List.of();
        }

        Set<String> orgMemberIds = mentions.getData();
        List<PostMention> existingMentions = post.getMentions(); // managed collection

        // 1. Remove old mentions
        existingMentions.removeIf(em -> !orgMemberIds.contains(em.getMentionedMember().getPublicId()));

        // 2. Add new mentions
        for (String memberId : orgMemberIds) {
            boolean exists = existingMentions.stream()
                    .anyMatch(em -> em.getMentionedMember().getPublicId().equals(memberId));
            if (!exists) {
                PostMention postMention = new PostMention();
                postMention.setPost(post);
                postMention.setMentionedMember(orgMemberService.getOrgMemberByPublicId(memberId));
                existingMentions.add(postMention);
            }
        }

        return existingMentions;
    }

}
