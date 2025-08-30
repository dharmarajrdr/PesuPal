package com.pesupal.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebsocketException {

    private String message;
}
