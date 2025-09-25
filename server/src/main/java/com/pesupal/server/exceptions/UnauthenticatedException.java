package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends BaseException {

    public UnauthenticatedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
