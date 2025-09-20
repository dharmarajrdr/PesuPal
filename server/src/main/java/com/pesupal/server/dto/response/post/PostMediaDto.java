package com.pesupal.server.dto.response.post;

import com.pesupal.server.model.post.PostMedia;
import lombok.Data;

import java.net.URL;
import java.util.UUID;

@Data
public class PostMediaDto {

    private URL url;

    private UUID mediaId;

    private String extension;

    public static PostMediaDto fromPostMedia(PostMedia postMedia) {

        PostMediaDto postMediaDto = new PostMediaDto();
        postMediaDto.setMediaId(postMedia.getMediaId());
        return postMediaDto;
    }
}
