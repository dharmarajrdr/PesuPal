package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaUploadDto {

    private String name;

    private UUID mediaId;

    private String extension;

    private Long size;
}
