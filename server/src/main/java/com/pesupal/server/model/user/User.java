package com.pesupal.server.model.user;

import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "users")
public class User extends CreationTimeAuditable {

    private String email;

    private String phone;

    private Long password;

    @OneToOne
    private Social social;
}
