package com.pesupal.server.strategies.trending.posts;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.repository.post.PostRepository;
import com.pesupal.server.service.interfaces.post.TrendingPostsAnalyser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WindowBasedTrendingPostsAnalyser implements TrendingPostsAnalyser {

    private final PostRepository postRepository;

    /**
     * Analyse and return a list of trending posts for the given org.
     *
     * @param org
     * @param limit
     * @return
     */
    @Override
    public List<Post> analyseTrendingPosts(Org org, int limit) {
        
        return List.of();
    }
}
