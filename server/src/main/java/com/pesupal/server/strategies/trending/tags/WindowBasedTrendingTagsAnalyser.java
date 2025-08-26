package com.pesupal.server.strategies.trending.tags;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Tag;
import com.pesupal.server.repository.post.TagRepository;
import com.pesupal.server.service.interfaces.post.TrendingTagsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class WindowBasedTrendingTagsAnalyser implements TrendingTagsAnalyser {

    private final TagRepository tagRepository;

    private final static Integer WINDOW_SIZE_IN_HOURS = 6;

    /**
     * Analyse tags based on the past WINDOW_SIZE_IN_HOURS hours and return the top 'limit' tags.
     *
     * @param limit
     * @return
     */
    @Override
    @Transactional
    public List<Tag> analyseTrendingTags(Org org, int limit) {

        return List.of();
    }
}
