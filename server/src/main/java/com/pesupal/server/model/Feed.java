package com.pesupal.server.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feed {

    @Id
    private Long feedId;

    private Long orgId;

    private Long userId;

    private String message;

    private Long feedConfigId;

    private Integer socialId;

    private List<String> files;
}
