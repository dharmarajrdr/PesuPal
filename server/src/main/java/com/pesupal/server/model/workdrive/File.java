package com.pesupal.server.model.workdrive;

import com.pesupal.server.enums.Security;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class File extends PublicAccessModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Folder folder;

    private Long size;

    @ManyToOne
    private OrgMember creator;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Enumerated(EnumType.STRING)
    private Security security;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<FileAccessStat> accessStats;
}
