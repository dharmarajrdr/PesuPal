package com.pesupal.server.model.post;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Tag extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;
}
