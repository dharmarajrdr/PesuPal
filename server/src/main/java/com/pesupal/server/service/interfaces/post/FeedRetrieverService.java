package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.user.OrgMember;

public interface FeedRetrieverService {

    PostsListDto getFeeds(int page, int size, SortOrder sortOrder, OrgMember currentOrgMember);
}
