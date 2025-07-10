package com.pesupal.server.model.workdrive;

import com.pesupal.server.enums.Security;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PublicFolder extends BaseModel {

    @OneToOne
    private Folder folder;

    @Enumerated(EnumType.STRING)
    private Security security;

    private boolean readable;

    private boolean writable;

    private boolean deletable;
}
