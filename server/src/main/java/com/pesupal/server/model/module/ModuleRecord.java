package com.pesupal.server.model.module;

import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class ModuleRecord extends PublicAccessModel {

    @ManyToOne
    private Module module;

    @Column(nullable = false)
    private String subject;

    @ManyToOne
    @Column(updatable = false)
    private OrgMember createdBy;

    @ManyToOne
    private OrgMember updatedBy;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "record")
    private List<RecordNote> notes;
}
