package com.pesupal.server.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MediaUploadDto {

    private String name;

    private UUID mediaId;

    private String extension;

    private Long size;
}
