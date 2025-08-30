package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Extension {

    PNG("png", ".png");

    private final String name;
    private final String extension;

    public static Extension fromContentType(String contentType) {
        if (contentType == null || !contentType.contains("/")) {
            throw new IllegalArgumentException("Invalid content type: " + contentType);
        }
        String extension = contentType.substring(contentType.lastIndexOf('/') + 1);
        return fromString(extension);
    }

    public static Extension fromString(String extension) {
        for (Extension ext : Extension.values()) {
            if (ext.getName().equalsIgnoreCase(extension)) {
                return ext;
            }
        }
        throw new IllegalArgumentException("Unsupported file type: " + extension);
    }

}
