package com.pesupal.server.model.post;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PostTag extends BaseModel {

    @ManyToOne
    private Post post;

    @ManyToOne
    private Tag tag;
}
