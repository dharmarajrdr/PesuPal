package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class FeatureNotImplementedException extends BaseException {

    public FeatureNotImplementedException(String message) {
        super(message, HttpStatus.NOT_IMPLEMENTED);
    }
}
