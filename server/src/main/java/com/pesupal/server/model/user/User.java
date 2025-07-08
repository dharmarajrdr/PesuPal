package com.pesupal.server.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends CreationTimeAuditable {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Long password;
}
