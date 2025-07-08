package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends BaseException {

    public PermissionDeniedException(String message) {

        super(message, HttpStatus.FORBIDDEN);
    }
}
