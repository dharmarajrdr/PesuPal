package com.pesupal.server.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreationTimeAuditable extends BaseModel {

    private LocalDateTime createdAt;
}
