package com.pesupal.server.factory;

import com.pesupal.server.enums.FeedRetriever;
import com.pesupal.server.exceptions.FeatureNotImplementedException;
import com.pesupal.server.service.interfaces.post.FeedRetrieverService;
import com.pesupal.server.strategies.feeds.SimpleFeedRetrieverService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FeedRetrieverServiceFactory {

    private final SimpleFeedRetrieverService simpleFeedRetrieverService;

    public FeedRetrieverService getFeedRetrieverService(FeedRetriever strategy) {

        switch (strategy) {
            case SIMPLE_FEED_RETRIEVER_ALGORITHM: {
                return simpleFeedRetrieverService;
            }
            case ADVANCED_FEED_RETRIEVER_ALGORITHM:
            case MACHINE_LEARNING_FEED_RETRIEVER_ALGORITHM:
            case PERSONALIZED_FEED_RETRIEVER_ALGORITHM: {
                throw new FeatureNotImplementedException("Feed retriever strategy not implemented yet: " + strategy);
            }
            default: {
                throw new IllegalArgumentException("Unsupported feed retriever strategy: " + strategy);
            }
        }
    }
}
