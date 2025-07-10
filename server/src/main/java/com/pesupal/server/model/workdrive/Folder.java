package com.pesupal.server.model.workdrive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Folder extends CreationTimeAuditable {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Org org;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Workspace space;

    @ManyToOne
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Folder> subFolders;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<File> files;
}
