package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class MandatoryDataMissingException extends BaseException {

    public MandatoryDataMissingException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
