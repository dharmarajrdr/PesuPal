package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PostMention extends BaseModel {

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JsonIgnore
    private OrgMember mentionedMember;
}
