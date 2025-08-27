package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;

public interface BookmarkService {

    void createBookmark(String postId);

    PostsListDto getAllBookmarkedPosts(int page, int size);

    void removeBookmark(String postId);

    boolean isBookmarked(Post post, OrgMember orgMember);
}
