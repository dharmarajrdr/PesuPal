package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MemberInfo {

    @Id
    private Long orgIid;

    private Long userId;

    private String displayName;

    private Integer roleId;

    private String profilePicture;

    private Integer visibilityId;
}
