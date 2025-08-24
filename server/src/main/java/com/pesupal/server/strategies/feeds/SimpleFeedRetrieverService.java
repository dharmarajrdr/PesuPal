package com.pesupal.server.strategies.feeds;

import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.post.FeedRetrieverService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleFeedRetrieverService implements FeedRetrieverService {

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

        return null;
    }
}
