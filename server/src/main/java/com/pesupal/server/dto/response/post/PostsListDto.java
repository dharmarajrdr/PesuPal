package com.pesupal.server.dto.response.post;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PostsListDto {

    private List<PostDto> posts;

    private Map<String, Object> info;
}
