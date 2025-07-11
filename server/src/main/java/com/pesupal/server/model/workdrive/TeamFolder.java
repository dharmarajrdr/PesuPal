package com.pesupal.server.model.workdrive;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.department.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TeamFolder extends BaseModel {

    @OneToOne
    private Folder folder;

    @ManyToOne
    private Department department;

    @OneToOne
    private PublicFolder publicFolder;
}
