package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class ThirdPartyFailedException extends BaseException {

    public ThirdPartyFailedException(String message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }
}
