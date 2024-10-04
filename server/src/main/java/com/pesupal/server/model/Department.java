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
public class Department {

    @Id
    private Long departmentId;

    private String name;

    private String description;

    private Integer orgId;

    public enum Visibility {
        PUBLIC,
        PRIVATE
    };

}
