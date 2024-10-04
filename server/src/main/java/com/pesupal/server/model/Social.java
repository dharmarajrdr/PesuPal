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
public class Social {

    @Id
    private Long socialId;

    private String linkedin;

    private String instagram;

    private String github;

}
