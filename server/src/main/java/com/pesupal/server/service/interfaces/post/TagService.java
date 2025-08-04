package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.model.post.Tag;

public interface TagService {

    Tag createOrGet(String tagName);
}
