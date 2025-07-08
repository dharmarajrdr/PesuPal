package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.OrgRepository;
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrgServiceImpl implements OrgService {

    private final UserService userService;
    private final OrgRepository orgRepository;

    /**
     * Gets an organization by its ID.
     *
     * @param orgId
     * @return Org
     */
    @Override
    public Org getOrgById(Long orgId) {

        return orgRepository.findById(orgId).orElseThrow(() -> new DataNotFoundException("Org with id " + orgId + " not found"));
    }

    /**
     * Creates a new organization.
     *
     * @param createOrgDto
     * @return Org
     */
    @Override
    public Org createOrg(CreateOrgDto createOrgDto) {

        User owner = userService.getUserById(createOrgDto.getOwnerId());
        Org org = createOrgDto.toOrg();
        org.setOwner(owner);
        return orgRepository.save(org);
    }
}
