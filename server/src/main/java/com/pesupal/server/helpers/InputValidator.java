package com.pesupal.server.helpers;

import com.pesupal.server.exceptions.MandatoryDataMissingException;

public class InputValidator {

    public static Object notNull(Object object, String name) {
        if (object == null) {
            throw new MandatoryDataMissingException(
                    String.format("Mandatory data missing: %s cannot be null", name)
            );
        }
        return object;
    }
}
