package com.pesupal.server.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.PublicAccessModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends PublicAccessModel {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
