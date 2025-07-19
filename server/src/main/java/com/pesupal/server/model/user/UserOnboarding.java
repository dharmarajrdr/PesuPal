package com.pesupal.server.model.user;

import com.pesupal.server.model.UUIDBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class UserOnboarding extends UUIDBaseModel {

    @OneToOne
    private User user;

    private boolean emailVerificationDone;

    private boolean phoneVerificationDone;
}
