package com.pesupal.server.model.workdrive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.Security;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class PublicFolder extends BaseModel {

    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    private Folder folder;

    @Enumerated(EnumType.STRING)
    private Security security;

    private boolean readable;

    private boolean writable;

    private boolean deletable;
}
