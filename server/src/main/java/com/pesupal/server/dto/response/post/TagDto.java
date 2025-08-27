package com.pesupal.server.dto.response.post;

import com.pesupal.server.projections.PostTagProjection;
import lombok.Data;

@Data
public class TagDto {

    private String title;

    private long count;

    public static TagDto fromPostTagProjection(PostTagProjection postTagProjection) {

        TagDto tagDto = new TagDto();
        tagDto.setTitle(postTagProjection.getName());
        tagDto.setCount(postTagProjection.getCount());
        return tagDto;
    }
}
