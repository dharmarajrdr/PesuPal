package com.pesupal.server.strategies.feeds;

import com.pesupal.server.dto.response.post.PostDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.post.PostRepository;
import com.pesupal.server.service.interfaces.post.FeedRetrieverService;
import com.pesupal.server.service.interfaces.post.PostService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SimpleFeedRetrieverService implements FeedRetrieverService {

    private final PostService postService;
    private final PostRepository postRepository;

    public SimpleFeedRetrieverService(@Lazy PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    /**
     * A simple feed retriever that returns the list of posts user has not liked or commented on yet on the particular org in descending order of creation.
     *
     * @param page
     * @param size
     * @param sortOrder
     * @param orgMember
     * @return PostsListDto
     * @algorithm SIMPLE_FEED_RETRIEVER_ALGORITHM
     */
    @Override
    public PostsListDto getFeeds(int page, int size, SortOrder sortOrder, OrgMember orgMember) {

        Sort sort = Sort.by(sortOrder == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = postRepository.getUnlikedPostsByOrgMember(orgMember.getOrg(), orgMember, pageable);
        List<PostDto> postDtos = new ArrayList<>(posts.getContent().stream().map(post -> postService.getPostDtoFromPostAndOrgMember(post, post.getCreator())).toList());

        PostsListDto postsListDto = new PostsListDto();
        postsListDto.setInfo(Map.of("hasMoreRecords", posts.hasNext()));
        postsListDto.setPosts(postDtos);
        return postsListDto;
    }
}
