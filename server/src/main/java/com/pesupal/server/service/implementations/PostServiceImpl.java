package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.dto.response.PostImpressionDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.post.PostMedia;
import com.pesupal.server.model.post.PostTag;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.PostRepository;
import com.pesupal.server.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final OrgService orgService;
    private final TagService tagService;
    private final UserService userService;
    private final PostRepository postRepository;
    private final OrgMemberService orgMemberService;

    /**
     * Creates a new post.
     *
     * @param createPostDto
     */
    @Override
    public Post createPost(CreatePostDto createPostDto, Long userId, Long orgId) {

        Org org = orgService.getOrgById(orgId);
        User user = userService.getUserById(userId);

        orgMemberService.validateUserIsOrgMember(user, org);

        Post post = createPostDto.toPost();
        post.setOrg(org);
        post.setUser(user);
        post.setStatus(PostStatus.PUBLISHED);
        List<PostMedia> postMedia = createPostDto.getMediaIds().stream().map(mediaId -> PostMedia.builder().post(post).mediaId(mediaId).build()).collect(Collectors.toList());
        post.setMedia(!postMedia.isEmpty());
        post.setPostMedia(postMedia);
        List<PostTag> postTags = createPostDto.getTags().stream().map(tagName -> PostTag.builder().post(post).tag(tagService.createOrGet(tagName)).build()).collect(Collectors.toList());
        post.setTags(postTags);
        return postRepository.save(post);
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId
     * @param userId
     * @param orgId
     * @return PostDto
     */
    @Override
    public PostDto getPostById(Long postId, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        Post post = postRepository.findByIdAndOrgId(postId, orgId).orElseThrow(() -> new DataNotFoundException("Post not found with ID: " + postId));
        PostDto postDto = PostDto.fromPost(post);
        postDto.setTags(post.getTags().stream().map(postTag -> postTag.getTag().getName()).toList());
        postDto.setMedia(post.getPostMedia().stream().map(PostMedia::getMediaId).toList());
        postDto.setOwner(UserBasicInfoDto.fromOrgMember(orgMember));
        postDto.setImpression(PostImpressionDto.builder().likes(post.getLikes().size()).comments(post.getComments().size()).build());
        return postDto;
    }
}
