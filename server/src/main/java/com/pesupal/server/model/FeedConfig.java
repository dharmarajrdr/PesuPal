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
public class FeedConfig {

    @Id
    private Long feedConfigId;

    private Boolean canComment;

    private String displayName;

    private Integer contactId;

    private Integer socialId;
}
