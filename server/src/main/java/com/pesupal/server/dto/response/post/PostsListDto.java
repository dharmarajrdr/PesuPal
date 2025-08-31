package com.pesupal.server.dto.response.post;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PostsListDto {

    private List<PostDto> posts = new ArrayList<>();

    private Map<String, Object> info = new HashMap<>();
}
