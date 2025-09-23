package com.pesupal.server.factory;

import com.pesupal.server.service.interfaces.post.TrendingPostsAnalyser;
import com.pesupal.server.strategies.trending.posts.EngagementBasedTrendingPostsAnalyser;
import com.pesupal.server.strategies.trending.posts.WindowBasedTrendingPostsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrendingPostsAnalyserFactory {

    private final WindowBasedTrendingPostsAnalyser windowBasedTrendingPostsAnalyser;
    private final EngagementBasedTrendingPostsAnalyser engagementBasedTrendingPostsAnalyser;

    public TrendingPostsAnalyser getTrendingPostsAnalyser(String strategy) {

        switch (strategy) {
            case "WINDOW_BASED": {
                return windowBasedTrendingPostsAnalyser;
            }
            case "ENGAGEMENT_BASED": {
                return engagementBasedTrendingPostsAnalyser;
            }
            default: {
                throw new IllegalArgumentException("Invalid strategy: " + strategy);
            }
        }
    }
}
