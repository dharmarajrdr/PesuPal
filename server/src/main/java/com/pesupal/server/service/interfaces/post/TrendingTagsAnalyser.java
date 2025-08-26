package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Tag;

import java.util.List;

public interface TrendingTagsAnalyser {

    List<Tag> analyseTrendingTags(Org org, int limit);
}
