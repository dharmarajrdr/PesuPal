package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class PostTag extends BaseModel {

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JsonIgnore
    private Tag tag;
}
