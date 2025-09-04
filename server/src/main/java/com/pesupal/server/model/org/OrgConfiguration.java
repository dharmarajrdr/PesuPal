package com.pesupal.server.model.org;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OrgConfiguration extends BaseModel {

    @ManyToOne
    private Org org;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean inviteMember;

    private boolean addMember;

    private boolean removeMember;

    private boolean updateMember;

    private boolean updateOrg;

    private boolean deleteOrg;

    private boolean leaveOrg;

    private boolean createGroup;

    private boolean createDepartment;

    public static OrgConfiguration getInitialConfiguration(Role role) {

        OrgConfiguration orgConfiguration = new OrgConfiguration();
        orgConfiguration.setCreateGroup(true);
        orgConfiguration.setCreateDepartment(true);
        switch (role) {
            case ADMIN: {
                orgConfiguration.setRole(role);
                orgConfiguration.setInviteMember(true);
                orgConfiguration.setAddMember(true);
                orgConfiguration.setRemoveMember(true);
                orgConfiguration.setUpdateMember(true);
                orgConfiguration.setUpdateOrg(true);
                orgConfiguration.setDeleteOrg(true);
                orgConfiguration.setLeaveOrg(true);
                orgConfiguration.setCreateDepartment(false);
                break;
            }
            case USER: {
                orgConfiguration.setRole(role);
                orgConfiguration.setInviteMember(false);
                orgConfiguration.setAddMember(false);
                orgConfiguration.setRemoveMember(false);
                orgConfiguration.setUpdateMember(false);
                orgConfiguration.setUpdateOrg(false);
                orgConfiguration.setDeleteOrg(false);
                orgConfiguration.setLeaveOrg(true);
                orgConfiguration.setCreateDepartment(false);
                break;
            }
            default: {
                throw new IllegalArgumentException("Default configuration not found for role: " + role);
            }
        }
        return orgConfiguration;
    }

}
