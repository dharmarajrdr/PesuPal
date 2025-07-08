package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.OrgRepository;
import com.pesupal.server.service.interfaces.OrgConfigurationService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {

    private final UserService userService;
    private final OrgRepository orgRepository;
    private final OrgConfigurationService orgConfigurationService;
    private final OrgMemberService orgMemberService;

    public OrgServiceImpl(UserService userService, OrgRepository orgRepository, OrgConfigurationService orgConfigurationService, @Lazy OrgMemberService orgMemberService) {
        this.userService = userService;
        this.orgRepository = orgRepository;
        this.orgConfigurationService = orgConfigurationService;
        this.orgMemberService = orgMemberService;
    }

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
    @Transactional
    public Org createOrg(CreateOrgDto createOrgDto) {

        User owner = userService.getUserById(createOrgDto.getOwnerId());
        Org org = createOrgDto.toOrg();
        org.setOwner(owner);
        org.setActive(true);
        org = orgRepository.save(org);
        orgConfigurationService.initializeOrgConfiguration(org);
        orgMemberService.joinOrgAsFirstMember(owner, org);
        return org;
    }
}
