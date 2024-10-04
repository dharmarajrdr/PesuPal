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
public class Channel {

    @Id
    private Long channelId;

    private String name;

    private String profilePicture;

    private enum visibility {
        PUBLIC,
        PRIVATE
    };

    private int orgId;
}
