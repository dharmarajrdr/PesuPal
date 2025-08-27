package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.response.post.QuoteDto;

public interface FeedsService {

    QuoteDto getQuoteOfTheDay();
}
