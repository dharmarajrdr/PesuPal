package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class ActionProhibitedException extends BaseException {

    public ActionProhibitedException(String message) {

        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
