package com.pesupal.server.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.PublicAccessModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupMessageMediaFile extends PublicAccessModel {

    @OneToOne
    @JsonIgnore
    private GroupChatMessage groupChatMessage;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Column(nullable = false)
    private String extension;
}
