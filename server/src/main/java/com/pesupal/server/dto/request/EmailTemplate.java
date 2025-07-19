package com.pesupal.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class EmailTemplate {

    private String subject;

    private String body;
}
