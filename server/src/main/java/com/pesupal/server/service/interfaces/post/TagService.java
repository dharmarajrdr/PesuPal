package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.model.post.Tag;

import java.util.List;

public interface TagService {

    Tag createOrGet(String tagName);

    List<String> getTrendingTags(int limit);
}
