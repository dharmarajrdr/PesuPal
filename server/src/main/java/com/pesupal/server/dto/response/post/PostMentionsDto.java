package com.pesupal.server.dto.response.post;

import com.pesupal.server.dto.response.UserPreviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostMentionsDto {

    private String label;

    private List<UserPreviewDto> data;
}
