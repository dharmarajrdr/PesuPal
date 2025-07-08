package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateDataReceivedException extends BaseException {

    public DuplicateDataReceivedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
