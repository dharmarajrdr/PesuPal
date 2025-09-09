package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.dto.response.post.QuoteDto;
import com.pesupal.server.enums.SortOrder;

public interface FeedsService {

    QuoteDto getQuoteOfTheDay();

    PostsListDto getFeeds(int page, int size, SortOrder sortOrder);
}
