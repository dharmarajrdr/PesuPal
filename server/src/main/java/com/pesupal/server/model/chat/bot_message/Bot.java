package com.pesupal.server.model.chat.bot_message;

import com.pesupal.server.model.PublicAccessModel;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Bot extends PublicAccessModel {

    private String name;

    private String description;

    private String displayPicture;
}
