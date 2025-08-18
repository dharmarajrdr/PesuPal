package com.pesupal.server.model.chat.bot_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class BotMessageMedia extends BaseModel {

    @OneToOne
    @JsonIgnore
    private BotMessage botMessage;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long size;
}
