package com.pesupal.server.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostImpressionDto {

    private int likes;

    private int comments;
}
