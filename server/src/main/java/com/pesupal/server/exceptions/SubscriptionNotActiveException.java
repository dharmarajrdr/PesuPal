package com.pesupal.server.exceptions;

import org.springframework.http.HttpStatus;

public class SubscriptionNotActiveException extends BaseException {

    public SubscriptionNotActiveException(Long orgId) {

        super("Org with ID " + orgId + " does not have an active subscription.", HttpStatus.FORBIDDEN);
    }
}
