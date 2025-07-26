package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.OrgRepository;
import com.pesupal.server.service.interfaces.*;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {

    private final UserService userService;
    private final OrgRepository orgRepository;
    private final OrgMemberService orgMemberService;
    private final UserOnboardingService userOnboardingService;
    private final OrgConfigurationService orgConfigurationService;
    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    public OrgServiceImpl(UserService userService, OrgRepository orgRepository, OrgConfigurationService orgConfigurationService, @Lazy OrgMemberService orgMemberService, @Lazy OrgSubscriptionHistoryService orgSubscriptionHistoryService, UserOnboardingService userOnboardingService) {
        this.userService = userService;
        this.orgRepository = orgRepository;
        this.orgMemberService = orgMemberService;
        this.userOnboardingService = userOnboardingService;
        this.orgConfigurationService = orgConfigurationService;
        this.orgSubscriptionHistoryService = orgSubscriptionHistoryService;
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
    public Org createOrg(CreateOrgDto createOrgDto, String userPublicId) {

        User owner = userService.getUserByPublicId(userPublicId);

        userOnboardingService.hasDoneOnboardingVerification(owner);

        Org org = createOrgDto.toOrg();
        org.setOwner(owner);
        org.setActive(true);
        org = orgRepository.save(org);
        orgConfigurationService.initializeOrgConfiguration(org);
        orgMemberService.joinOrgAsFirstMember(owner, org);
        orgSubscriptionHistoryService.addSubscription(org.getId(), "FREE_TRIAL", null);
        return org;
    }
}
