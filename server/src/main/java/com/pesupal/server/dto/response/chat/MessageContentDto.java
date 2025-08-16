package com.pesupal.server.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.WebsitePreviewDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContentDto extends WebsitePreviewDto {

    private String content;
}
