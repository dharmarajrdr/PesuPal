package com.pesupal.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsitePreviewDto {

    private String title;

    private String description;

    private String image;
}
