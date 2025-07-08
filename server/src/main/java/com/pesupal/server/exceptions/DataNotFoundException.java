package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BaseException {

    public DataNotFoundException(String message) {

        super(message, HttpStatus.NOT_FOUND);
    }
}
