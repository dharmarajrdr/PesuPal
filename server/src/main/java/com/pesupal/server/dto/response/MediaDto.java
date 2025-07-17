package com.pesupal.server.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class MediaDto {

    private UUID id;

    private String extension;

    @Override
    public String toString() {

        return id.toString() + "." + extension;
    }
}
