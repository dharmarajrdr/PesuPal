package com.pesupal.server.model.user;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Social extends BaseModel {

    private String linkedin;

    private String instagram;

    private String github;

    private String twitter;
}
