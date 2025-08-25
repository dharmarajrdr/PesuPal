package com.pesupal.server.dto.request.post;

import lombok.Data;

import java.util.Set;

@Data
public class CreatePostMentionsDto {

    private String label;

    private Set<String> data;
}
