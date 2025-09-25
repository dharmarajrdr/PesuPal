package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;

import java.util.List;

public interface TrendingPostsAnalyser {

    List<Post> analyseTrendingPosts(Org org, int limit);
}
