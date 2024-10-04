package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProfileVisibility {

    @Id
    private Long visibilityId;

    private Long userId;

    private enum profilePicture {
        PUBLIC,
        PRIVATE
    };

    private enum linkedin {
        PUBLIC,
        PRIVATE
    };

    private enum instagram {
        PUBLIC,
        PRIVATE
    };

    private enum github {
        PUBLIC,
        PRIVATE
    };
}
