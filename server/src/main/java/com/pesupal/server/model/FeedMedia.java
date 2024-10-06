package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedMedia {

    @Id
    @SequenceGenerator(name = "feed_media_id_seq", sequenceName = "feed_media_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feed_media_id_seq")
    private Long feedMediaId;

    @Enumerated(EnumType.STRING) // This ensures the enum is stored as a string in the database
    private mediaType mediaType; // mediaType is now mapped to a database column

    private enum mediaType {
        IMAGE, VIDEO
    }

    private String mediaUrl;

    // @ManyToOne
    // @JoinColumn(name = "feed_id") // Join column for the ManyToOne relationship
    // private Feed feed_field; // Reference to the Feed entity
}
