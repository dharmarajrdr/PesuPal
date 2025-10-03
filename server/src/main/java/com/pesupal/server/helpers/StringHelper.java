package com.pesupal.server.helpers;

import java.util.Optional;

public class StringHelper {

    /**
     * Converts a camelCase string to a title case string.
     *
     * @param input
     * @return
     * @example `helloWorld` becomes `Hello World`
     */
    public static String camelCaseToTitle(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Insert space before each uppercase letter, then trim and capitalize
        String result = input.replaceAll("([A-Z])", " $1").trim();

        // Capitalize the first letter of each word
        String[] words = result.split("\\s+");
        StringBuilder title = new StringBuilder();
        for (String word : words) {
            title.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return title.toString().trim();
    }

    /**
     * Converts a string with spaces to camelCase.
     *
     * @param input
     * @return
     */
    public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder camelCaseString = new StringBuilder();
        boolean nextUpperCase = false;

        for (char c : input.toCharArray()) {
            if (c == ' ') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCaseString.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    camelCaseString.append(Character.toLowerCase(c));
                }
            }
        }

        return camelCaseString.toString();
    }

    /**
     * Finds the first URL in a given string.
     *
     * @param input
     * @return
     */
    public static Optional<String> findFirstUrl(String input) {
        if (input == null || input.isEmpty()) {
            return Optional.empty();
        }

        String urlRegex = "(https?://[\\w-]+(\\.[\\w-]+)+(/\\S*)?)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(urlRegex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Optional.of(matcher.group(0));
        } else {
            return Optional.empty();
        }
    }

}
