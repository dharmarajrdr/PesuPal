package com.pesupal.server.strategies.trending.tags;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Tag;
import com.pesupal.server.repository.post.PostTagRepository;
import com.pesupal.server.service.interfaces.post.TrendingTagsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MostPostTrendingTagsAnalyser implements TrendingTagsAnalyser {

    private final PostTagRepository postTagRepository;

    /**
     * Analyse tags based on the most posts and return the top 'limit' tags.
     *
     * @param limit
     * @return
     */
    @Override
    @Transactional
    public List<Tag> analyseTrendingTags(Org org, int limit) {

        return postTagRepository.findTopTagsByPostCount(org, limit);
    }
}
