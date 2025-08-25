package com.pesupal.server.dto.request.post;

import lombok.Data;

import java.util.List;

@Data
public class CreatePostMentionsDto {

    private String label;

    private List<String> data;
}
