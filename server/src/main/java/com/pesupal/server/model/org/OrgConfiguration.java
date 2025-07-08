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

    private Boolean inviteMember;

    private Boolean addMember;

    private Boolean removeMember;

    private Boolean updateMember;

    private Boolean updateOrg;

    private Boolean deleteOrg;

    private Boolean leaveOrg;

    private Boolean createGroup;

    public static OrgConfiguration getInitialConfiguration(Role role) {

        OrgConfiguration orgConfiguration = new OrgConfiguration();
        orgConfiguration.setCreateGroup(true);
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
                break;
            }
            default: {
                throw new IllegalArgumentException("Default configuration not found for role: " + role);
            }
        }
        return orgConfiguration;
    }

}
