package com.pesupal.server.factory;

import com.pesupal.server.service.interfaces.post.TrendingTagsAnalyser;
import com.pesupal.server.strategies.trending.tags.MostPostTrendingTagsAnalyser;
import com.pesupal.server.strategies.trending.tags.WindowBasedTrendingTagsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrendingTagsAnalyserFactory {

    private final MostPostTrendingTagsAnalyser mostPostTrendingTagsAnalyser;
    private final WindowBasedTrendingTagsAnalyser windowBasedTrendingTagsAnalyser;

    public TrendingTagsAnalyser getTrendingTagsAnalyser(String strategy) {

        switch (strategy) {
            case "WINDOW_BASED": {
                return windowBasedTrendingTagsAnalyser;
            }
            case "MOST_POST": {
                return mostPostTrendingTagsAnalyser;
            }
            default: {
                throw new IllegalArgumentException("Unknown strategy: " + strategy);
            }
        }
    }
}
