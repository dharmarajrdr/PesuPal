package com.pesupal.server.helpers;

public class FileHelper {

    public static String getExtensionGroup(String extension) {
        switch (extension) {
            case "jpg", "jpeg", "png", "gif", "bmp", "svg", "tiff" -> {
                return "Image";
            }
            case "mp4", "avi", "mov", "wmv", "flv", "mkv" -> {
                return "Video";
            }
            case "mp3", "wav", "aac", "flac" -> {
                return "Audio";
            }
            default -> {
                return "Document";
            }
        }
    }
}
