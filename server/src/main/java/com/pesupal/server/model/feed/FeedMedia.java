package com.pesupal.server.model.feed;

import com.pesupal.server.enums.FileType;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class FeedMedia extends BaseModel {

    @ManyToOne
    private Feed feed;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private String mediaUrl;
}
