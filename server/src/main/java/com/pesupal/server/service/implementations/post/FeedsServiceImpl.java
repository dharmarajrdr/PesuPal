package com.pesupal.server.service.implementations.post;

import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.dto.response.post.QuoteDto;
import com.pesupal.server.enums.FeedRetriever;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.exceptions.ThirdPartyFailedException;
import com.pesupal.server.factory.FeedRetrieverServiceFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.post.FeedRetrieverService;
import com.pesupal.server.service.interfaces.post.FeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class FeedsServiceImpl extends CurrentValueRetriever implements FeedsService {

    @Value("${ninja.api.key}")
    private String ninjaApiKey;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final static String DAILY_QUOTE_KEY = "daily-quotes";
    @Autowired
    private FeedRetrieverServiceFactory feedRetrieverServiceFactory;

    private final static FeedRetriever feedRetrieverAlgorithm = FeedRetriever.SIMPLE_FEED_RETRIEVER_ALGORITHM;

    /**
     * Fetched the quote of the day from third party service
     *
     * @return
     */
    @Override
    public QuoteDto getQuoteOfTheDay() {

        Object cachedQuote = redisTemplate.opsForValue().get(DAILY_QUOTE_KEY);
        if (cachedQuote != null) {
            return (QuoteDto) cachedQuote;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.api-ninjas.com/v1/quotes";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", ninjaApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<QuoteDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, QuoteDto[].class);
        if (response.getBody() != null && response.getBody().length > 0) {
            QuoteDto quoteDto = response.getBody()[0];
            // expire on 12AM the next day
            Duration TTL = Duration.between(java.time.LocalDateTime.now(), java.time.LocalDateTime.now().toLocalDate().atStartOfDay().plusDays(1));
            redisTemplate.opsForValue().set(DAILY_QUOTE_KEY, quoteDto, TTL);
            return quoteDto;
        }
        throw new ThirdPartyFailedException("Failed to fetch quote of the day from third party service.");
    }

    /**
     * Retrieves the feed for the current user.
     *
     * @param page
     * @param size
     * @param sortOrder
     * @return
     */
    @Override
    public PostsListDto getFeeds(int page, int size, SortOrder sortOrder) {

        FeedRetrieverService feedRetrieverService = feedRetrieverServiceFactory.getFeedRetrieverService(feedRetrieverAlgorithm);
        return feedRetrieverService.getFeeds(page, size, sortOrder, getCurrentOrgMember());
    }
}
