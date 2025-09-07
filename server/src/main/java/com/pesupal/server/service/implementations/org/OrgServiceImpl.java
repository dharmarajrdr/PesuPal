package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.CreateOrgDto;
import com.pesupal.server.dto.request.org.OrgDetailsDto;
import com.pesupal.server.dto.response.org.OrgCreatedDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.OrgHelper;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.org.OrgRepository;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.service.interfaces.org.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgServiceImpl implements OrgService {

    private final UserService userService;
    private final OrgRepository orgRepository;
    private final OrgMemberService orgMemberService;
    private final UserOnboardingService userOnboardingService;
    private final OrgConfigurationService orgConfigurationService;
    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    public OrgServiceImpl(@Lazy UserService userService, OrgRepository orgRepository, OrgConfigurationService orgConfigurationService, @Lazy OrgMemberService orgMemberService, @Lazy OrgSubscriptionHistoryService orgSubscriptionHistoryService, @Lazy UserOnboardingService userOnboardingService) {
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
     * Validates the organization creation request.
     *
     * @param createOrgDto
     */
    private void validateBeforeOrgCreation(CreateOrgDto createOrgDto) {

        OrgDetailsDto orgDetailsDto = createOrgDto.getOrg();

        if (orgRepository.existsByUniqueName(orgDetailsDto.getUniqueName())) {
            throw new DuplicateDataReceivedException("Org name '" + orgDetailsDto.getUniqueName() + "' is already taken. Please choose a different name.");
        }
    }

    /**
     * Creates a new organization.
     *
     * @param createOrgDto
     * @return Org
     */
    @Override
    @Transactional
    public OrgCreatedDto createOrg(CreateOrgDto createOrgDto, String userPublicId) {

        User owner = userService.getUserByPublicId(userPublicId);

        userOnboardingService.hasDoneOnboardingVerification(owner);
        validateBeforeOrgCreation(createOrgDto);

        Org org = createOrgDto.getOrg().toOrg();
        org.setOwner(owner);
        org.setActive(true);
        orgRepository.save(org);

        OrgMember orgMember = orgMemberService.joinOrgAsFirstMember(createOrgDto, org, owner);
        orgConfigurationService.initializeOrgConfiguration(orgMember);
        orgSubscriptionHistoryService.addSubscription(org.getId(), "FREE_TRIAL", null);
        return OrgCreatedDto.fromOrgMember(orgMember);
    }

    /**
     * Deletes an organization permanently.
     *
     * @param orgMember
     */
    @Override
    @Transactional
    public void deleteOrg(OrgMember orgMember) {

        Org org = orgMember.getOrg();

        if (!OrgHelper.isOrgOwner(orgMember.getUser().getPublicId(), orgMember.getOrg())) {
            throw new PermissionDeniedException("You do not have permission to delete this organization.");
        }

        org.setActive(false);
        orgRepository.save(org);

        // Remove all org members - To restrict access to the org
        orgMemberService.removeAllOrgMembers(org);

        // Stop all schedules associated with the org

        // Garbage collect all data associated with the org - Scheduled task
    }

    /**
     * Allows an org member to leave the organization.
     *
     * @param orgMember
     */
    @Transactional
    @Override
    public void leaveOrg(OrgMember orgMember) {

        Org org = orgMember.getOrg();

        if (OrgHelper.isOrgOwner(orgMember.getPublicId(), org)) {
            throw new ActionProhibitedException("You cannot leave the organization as you are the owner. Please transfer ownership or delete the organization.");
        }

        orgMemberService.removeOrgMember(orgMember);

        // Stop all schedules associated with the user in the org
    }

    /**
     * Updates organization details.
     *
     * @param orgPublicId
     * @param createOrgDto
     * @param currentOrgMember
     */
    @Override
    public void updateOrg(String orgPublicId, CreateOrgDto createOrgDto, OrgMember currentOrgMember) {

        Org org = currentOrgMember.getOrg();
        if (!org.getPublicId().equals(orgPublicId)) {
            throw new ActionProhibitedException("You are not allowed to update this organization.");
        }

        if (!org.getOwner().getId().equals(currentOrgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to update this organization.");
        }

        OrgDetailsDto orgDetailsDto = createOrgDto.getOrg();
        if (orgDetailsDto.getUniqueName() != null && !orgDetailsDto.getUniqueName().equals(org.getUniqueName())) {
            if (orgRepository.existsByUniqueName(orgDetailsDto.getUniqueName())) {
                throw new DuplicateDataReceivedException("Org name '" + orgDetailsDto.getUniqueName() + "' is already taken. Please choose a different name.");
            }
            org.setUniqueName(orgDetailsDto.getUniqueName());
        }

        if (orgDetailsDto.getDisplayName() != null) {
            org.setDisplayName(orgDetailsDto.getDisplayName());
        }

        if (orgDetailsDto.getDisplayPicture() != null) {
            org.setDisplayPicture(orgDetailsDto.getDisplayPicture());
        }

        orgRepository.save(org);
    }

    /**
     * Clears all data in the deleted organization.
     * This method is intended to be called by a scheduled task to permanently remove all data associated with a deleted organization.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void clearAllDataInTheDeletedOrg() {

    }

}
