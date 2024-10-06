package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FeedConfig {

    @Id
    @SequenceGenerator(name = "feed_config_id_seq", sequenceName = "feed_config_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "feed_config_id_seq", strategy = GenerationType.SEQUENCE)
    private Long feedConfigId;

    private Boolean canComment;

    private Boolean canShare;

    private Boolean canBookmark;
}
