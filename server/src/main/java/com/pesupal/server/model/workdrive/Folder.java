package com.pesupal.server.model.workdrive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString(onlyExplicitlyIncluded = true)
public class Folder extends PublicAccessModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Org org;

    @ManyToOne
    private User owner;

    private Long size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Workspace space;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Folder> subFolders;

    @OneToOne(mappedBy = "folder", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private PublicFolder publicFolder;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<File> files;
}
