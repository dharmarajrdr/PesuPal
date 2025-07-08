package com.pesupal.server.model.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Org extends CreationTimeAuditable {

    @Column(nullable = false)
    private String displayName;

    @Column(unique = true, nullable = false)
    private String uniqueName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;

    private String displayPicture;

    private Boolean isActive;
}
