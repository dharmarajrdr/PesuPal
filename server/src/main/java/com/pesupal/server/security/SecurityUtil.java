package com.pesupal.server.security;

import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtil {

    private final UserService userService;

    /**
     * Retrieves the email of the currently authenticated user.
     *
     * @return String - the email of the current user
     * @throws PermissionDeniedException if the user is not authenticated or if the email cannot be retrieved
     */
    public String getCurrentUserEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new PermissionDeniedException("User not authenticated.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // usually the email
        } else if (principal instanceof String) {
            return (String) principal; // in case JWT stores email directly as subject
        }

        throw new PermissionDeniedException("Unable to retrieve user email from authentication principal.");
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return User - the current user entity
     */
    public Long getCurrentUserId() {

        return userService.getUserByEmail(getCurrentUserEmail()).getId();
    }
}
