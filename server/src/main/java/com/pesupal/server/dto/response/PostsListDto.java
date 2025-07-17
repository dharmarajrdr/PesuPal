package com.pesupal.server.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PostsListDto {

    private List<PostDto> posts;

    private Map<String, Object> info;
}
