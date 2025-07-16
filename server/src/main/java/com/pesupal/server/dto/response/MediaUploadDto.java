package com.pesupal.server.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediaUploadDto {

    private String name;

    private String extension;

    private Long size;
}
