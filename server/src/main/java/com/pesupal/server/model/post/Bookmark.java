package com.pesupal.server.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bookmark extends CreationTimeAuditable {

    @ManyToOne
    @JsonIgnore
    private Post post;
    
    @ManyToOne
    @JsonIgnore
    private OrgMember orgMember;
}
