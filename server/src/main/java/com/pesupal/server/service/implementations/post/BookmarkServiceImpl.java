package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Bookmark;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.post.BookmarkRepository;
import com.pesupal.server.service.interfaces.post.BookmarkService;
import com.pesupal.server.service.interfaces.post.PostService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookmarkServiceImpl extends CurrentValueRetriever implements BookmarkService {

    private final PostService postService;
    private final BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(@Lazy PostService postService, BookmarkRepository bookmarkRepository) {
        this.postService = postService;
        this.bookmarkRepository = bookmarkRepository;
    }

    /**
     * Create a bookmark for a post.
     *
     * @param postId
     */
    @Override
    public void createBookmark(String postId) {

        OrgMember orgMember = getCurrentOrgMember();

        Post post = postService.getPostByPublicId(postId);

        if (!post.getStatus().equals(PostStatus.PUBLISHED)) {
            throw new PermissionDeniedException("The post you are trying to bookmark does not exist");
        }

        if (isBookmarked(post, orgMember)) {
            throw new ActionProhibitedException("You have already bookmarked this post");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setOrgMember(orgMember);
        bookmark.setPost(post);
        bookmarkRepository.save(bookmark);
    }

    /**
     * Get all bookmarked posts for the current user.
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PostsListDto getAllBookmarkedPosts(int page, int size) {

        OrgMember orgMember = getCurrentOrgMember();
        PostsListDto postsListDto = new PostsListDto();
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Bookmark> bookmarks = bookmarkRepository.findAllByOrgMemberOrderByCreatedAtDesc(orgMember, pageable);
        postsListDto.setPosts(bookmarks.stream().map(bookmark -> postService.getPostDtoFromPostAndOrgMember(bookmark.getPost(), orgMember)).toList());
        postsListDto.setInfo(Map.of("hasMoreRecords", bookmarks.hasNext()));
        return postsListDto;
    }

    /**
     * Remove a bookmark for a post.
     *
     * @param postId
     */
    @Override
    public void removeBookmark(String postId) {

        OrgMember orgMember = getCurrentOrgMember();
        Post post = postService.getPostByPublicId(postId);

        Bookmark bookmark = bookmarkRepository.findBookmarkByOrgMemberAndPost(orgMember, post).orElseThrow(() -> new PermissionDeniedException("You have not bookmarked this post yet"));
        bookmarkRepository.delete(bookmark);
    }

    /**
     * Check if a post is bookmarked by a user.
     *
     * @param post
     * @param orgMember
     * @return
     */
    @Override
    public boolean isBookmarked(Post post, OrgMember orgMember) {

        return bookmarkRepository.existsBookmarkByOrgMemberAndPost(orgMember, post);
    }
}
