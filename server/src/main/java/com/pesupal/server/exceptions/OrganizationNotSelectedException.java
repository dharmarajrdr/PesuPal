package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class OrganizationNotSelectedException extends BaseException {

    public OrganizationNotSelectedException() {
        super("You have not chosen an organization yet. Please select an organization to proceed.", HttpStatus.TEMPORARY_REDIRECT);
    }
}
