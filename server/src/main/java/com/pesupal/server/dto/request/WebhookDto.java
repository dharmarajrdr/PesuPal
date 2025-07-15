package com.pesupal.server.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WebhookDto {

    String url;

    List<String> events;
}
