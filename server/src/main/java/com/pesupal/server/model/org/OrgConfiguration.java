package com.pesupal.server.model.org;

import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
public class OrgConfiguration extends BaseModel {

    @ManyToOne
    private OrgRole role;

    @Enumerated(EnumType.STRING)
    private OrgAction permittedAction;

    /**
     * All Actions are permitted
     *
     * @param orgRole
     * @return
     */
    private static List<OrgConfiguration> superAdminConfigurations(OrgRole orgRole) {

        List<OrgConfiguration> superAdminConfigurations = new ArrayList<>();
        for (OrgAction orgAction : OrgAction.values()) {
            superAdminConfigurations.add(OrgConfiguration.builder().permittedAction(orgAction).role(orgRole).build());
        }
        return superAdminConfigurations;
    }

    /**
     * Limited actions are permitted by default
     *
     * @param orgRole
     * @return
     */
    private static List<OrgConfiguration> memberConfigurations(OrgRole orgRole) {

        List<OrgConfiguration> memberConfigurations = new ArrayList<>();
        List<OrgAction> memberDoableActions = List.of(
                // Add member do-able actions here
        );
        for (OrgAction orgAction : memberDoableActions) {
            memberConfigurations.add(OrgConfiguration.builder().permittedAction(orgAction).role(orgRole).build());
        }
        return memberConfigurations;
    }

    public static List<OrgConfiguration> getInitialConfiguration(OrgRole role) {

        switch (role.getName()) {
            case "SUPER_ADMIN": {
                return superAdminConfigurations(role);
            }
            case "MEMBER": {
                return memberConfigurations(role);
            }
            default: {
                throw new DataNotFoundException(role.getName() + " is not a default role.");
            }
        }
    }
}
