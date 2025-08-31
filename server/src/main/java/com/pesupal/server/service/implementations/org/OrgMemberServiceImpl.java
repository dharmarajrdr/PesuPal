package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.AddOrgMemberDto;
import com.pesupal.server.dto.request.org.CreateDesignationDto;
import com.pesupal.server.dto.request.org.CreateOrgDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.org.LatestSubscriptionDto;
import com.pesupal.server.dto.response.org.OrgDetailDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.OrgHelper;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.model.user.Designation;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.org.DepartmentRepository;
import com.pesupal.server.repository.org.OrgMemberRepository;
import com.pesupal.server.service.interfaces.AuthService;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageChatService;
import com.pesupal.server.service.interfaces.org.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgMemberServiceImpl implements OrgMemberService {

    private final OrgService orgService;
    private final UserService userService;
    private final AuthService authService;
    private final MediaService mediaService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final OrgMemberRepository orgMemberRepository;
    private final DepartmentRepository departmentRepository;
    private final OrgConfigurationService orgConfigurationService;
    private final DirectMessageChatService directMessageChatService;
    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    public OrgMemberServiceImpl(OrgService orgService, UserService userService, AuthService authService, @Lazy DepartmentService departmentService, DesignationService designationService, OrgMemberRepository orgMemberRepository, OrgConfigurationService orgConfigurationService, OrgSubscriptionHistoryService orgSubscriptionHistoryService, DirectMessageChatService directMessageChatService, DepartmentRepository departmentRepository, MediaService mediaService) {
        this.orgService = orgService;
        this.userService = userService;
        this.authService = authService;
        this.mediaService = mediaService;
        this.departmentService = departmentService;
        this.designationService = designationService;
        this.orgMemberRepository = orgMemberRepository;
        this.departmentRepository = departmentRepository;
        this.orgConfigurationService = orgConfigurationService;
        this.directMessageChatService = directMessageChatService;
        this.orgSubscriptionHistoryService = orgSubscriptionHistoryService;
    }

    /**
     * Retrieves an organization member by their public ID.
     *
     * @param publicId
     * @return
     */
    @Override
    public OrgMember getOrgMemberByPublicId(String publicId) {

        return orgMemberRepository.findByPublicId(publicId).orElseThrow(() -> new DataNotFoundException("User not found."));
    }

    /**
     * Retrieves an organization member by user and organization.
     *
     * @param user
     * @param org
     * @return OrgMember
     */
    @Override
    public OrgMember getOrgMemberByUserAndOrg(User user, Org org) {

        return orgMemberRepository.findByUserAndOrg(user, org).orElseThrow(() -> new DataNotFoundException("User with ID " + user.getId() + " is not a member of this org."));
    }

    /**
     * Retrieves an organization member by user ID and org ID.
     *
     * @param userId
     * @param orgId
     * @return OrgMember
     */
    @Override
    public OrgMember getOrgMemberByUserIdAndOrgId(Long userId, Long orgId) {

        User user = userService.getUserById(userId);
        Org org = orgService.getOrgById(orgId);
        OrgMember orgMember = getOrgMemberByUserAndOrg(user, org);
//      orgMember.getUser().setPassword(null);
        return orgMember;
    }

    /**
     * Checks if the role has the privilege to add a member to the organization.
     *
     * @param org
     * @param role
     * @return Boolean
     */
    private Boolean hasPrivilegeToAddMember(Org org, Role role) {

        OrgConfiguration orgConfiguration = orgConfigurationService.getOrgConfigurationByOrgAndRole(org, role);
        return orgConfiguration.getAddMember();
    }

    /**
     * Retrieve user's profile as basic info
     *
     * @param orgMember
     * @return
     */
    @Override
    public UserBasicInfoDto getUserBasicInfo(OrgMember orgMember) {
        UserBasicInfoDto userBasicInfoDto = UserBasicInfoDto.fromOrgMember(orgMember);
        userBasicInfoDto.setDisplayPicture(mediaService.generatePresignedUrl(orgMember.getDisplayPicture()));
        return userBasicInfoDto;
    }

    /**
     * Checks if the role has the privilege to update a member in the organization.
     *
     * @param org
     * @param role
     * @return
     */
    private Boolean hasPrivilegeToUpdateMember(Org org, Role role) {

        OrgConfiguration orgConfiguration = orgConfigurationService.getOrgConfigurationByOrgAndRole(org, role);
        return orgConfiguration.getUpdateMember();
    }

    /**
     * Counts the number of members in an organization.
     *
     * @param org
     * @return Integer
     */
    public Integer countOrgMembersByOrg(Org org) {

        return orgMemberRepository.countByOrg(org);
    }

    /**
     * Checks if a user is already a member of an organization.
     *
     * @param user
     * @param org
     * @return Boolean
     */
    @Override
    public Boolean existsByUserAndOrg(User user, Org org) {

        return orgMemberRepository.existsByUserAndOrg(user, org);
    }

    /**
     * Checks if a user is already a member of an organization by user ID and org ID.
     *
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public Boolean existsByUserIdAndOrgId(Long userId, Long orgId) {

        return orgMemberRepository.existsByUserIdAndOrgId(userId, orgId);
    }

    private Designation createDummyDesignationForNewOrg(Org org, String designationName) {

        CreateDesignationDto createDesignationDto = new CreateDesignationDto();
        createDesignationDto.setName(designationName);
        createDesignationDto.setSeniorityLevel(10L);
        createDesignationDto.setOrgId(org.getId());
        return designationService.createDesignation(createDesignationDto);
    }

    private Department createDummyDepartmentForNewOrg(String departmentName) {

        Department department = new Department();
        department.setName(departmentName);
        department.setHead(null);
        return departmentRepository.save(department);
    }

    /**
     * Joins an organization as the first member.
     *
     * @param createOrgDto
     */
    @Override
    public OrgMember joinOrgAsFirstMember(CreateOrgDto createOrgDto, Org org, User owner) {

        if (existsByUserAndOrg(owner, org)) {
            throw new ActionProhibitedException("You are already a member of this organization.");
        }

        Department department = createDummyDepartmentForNewOrg("Executive Department");
        Designation designation = createDummyDesignationForNewOrg(org, "CEO");

        OrgMember newOrgMember = createOrgDto.getUser().toOrgMember();
        newOrgMember.setAddedBy(null);
        newOrgMember.setManager(null);
        newOrgMember.setOrg(org);
        newOrgMember.setUser(owner);
        newOrgMember.setDepartment(department);
        newOrgMember.setDesignation(designation);
        newOrgMember.setEmployeeId(1);
        newOrgMember.setRole(Role.ADMIN);
        return orgMemberRepository.save(newOrgMember);
    }

    /**
     * Lists all organizations that a user is part of.
     *
     * @param userId
     * @return List<OrgDetailDto>
     */
    @Override
    public List<OrgDetailDto> listOfOrgUserPartOf(Long userId) {

        List<OrgDetailDto> orgDetailDtos = new ArrayList<>();
        User user = userService.getUserById(userId);
        List<OrgMember> orgMembers = orgMemberRepository.findByUser(user);
        orgMembers.sort((o1, o2) -> o1.getOrg().getDisplayName().compareToIgnoreCase(o2.getOrg().getDisplayName()));
        for (OrgMember orgMember : orgMembers) {
            Org org = orgMember.getOrg();
            Integer membersCount = countOrgMembersByOrg(org);
            OrgDetailDto orgDetailDto = OrgDetailDto.fromOrg(org);
            orgDetailDto.setRole(orgMember.getRole());
            orgDetailDto.setMembers(membersCount);
            orgDetailDto.setDisplayPicture(mediaService.generatePresignedUrl(org.getDisplayPicture()));
            OrgSubscriptionHistory orgSubscriptionHistory = orgSubscriptionHistoryService.getLatestSubscription(org.getId()).orElseThrow(() -> new DataNotFoundException("No subscription history found for org with ID " + org.getId()));
            orgDetailDto.setSubscription(LatestSubscriptionDto.fromOrgSubscriptionHistory(orgSubscriptionHistory));
            orgDetailDtos.add(orgDetailDto);
        }
        return orgDetailDtos;
    }

    /**
     * Adds a member to an organization.
     *
     * @param addOrgMemberDto
     * @return OrgMember
     */
    @Override
    public OrgMember addMemberToOrg(AddOrgMemberDto addOrgMemberDto, OrgMember currentUser, boolean firstMember) {

        Org org = currentUser.getOrg();
        User userToAdd = userService.getUserById(addOrgMemberDto.getUserId());

        OrgMember addedBy = firstMember ? null : currentUser;

        if (addedBy != null) {

            if (!hasPrivilegeToAddMember(org, addedBy.getRole())) {
                throw new PermissionDeniedException("You do not have permission to add members to this organization.");
            }
        }

        if (existsByUserAndOrg(userToAdd, org)) {
            throw new ActionProhibitedException("User is already a member of this organization.");
        }

        OrgMember manager = firstMember ? null : getOrgMemberByPublicId(addOrgMemberDto.getManagerId());

        OrgMember newOrgMember = addOrgMemberDto.toOrgMember();
        newOrgMember.setAddedBy(addedBy);
        newOrgMember.setManager(manager);
        newOrgMember.setOrg(org);
        newOrgMember.setUser(userToAdd);
        newOrgMember.setDepartment(departmentService.getDepartmentById(addOrgMemberDto.getDepartmentId()));
        newOrgMember.setDesignation(designationService.getDesignationById(addOrgMemberDto.getDesignationId()));
        newOrgMember.setEmployeeId(countOrgMembersByOrg(org));
        return orgMemberRepository.save(newOrgMember);
    }

    /**
     * Validates if a user is a member of an organization.
     *
     * @param user
     * @param org
     */
    @Override
    public void validateUserIsOrgMember(User user, Org org) {

        if (!existsByUserAndOrg(user, org)) {
            throw new DataNotFoundException("User with ID " + user.getId() + " is not a member of this org.");
        }
    }

    /**
     * Retrieves all members of an organization.
     *
     * @param currentOrgMember
     * @return
     */
    @Override
    public List<UserBasicInfoDto> getAllOrgMembers(OrgMember currentOrgMember) {

        Long orgId = currentOrgMember.getOrg().getId();

        return orgMemberRepository.findAllByOrgIdOrderByDisplayNameAsc(orgId).stream().map(orgMember -> {
            UserBasicInfoDto userBasicInfoDto = getUserBasicInfo(orgMember);
            DirectMessageChat directMessageChat = directMessageChatService.getOrCreateDirectMessageChat(currentOrgMember, orgMember);
            if (directMessageChat != null) {
                userBasicInfoDto.setChatId(directMessageChat.getPublicId());
            }
            return userBasicInfoDto;
        }).toList();
    }

    /**
     * Retrieves organization members based on a search query.
     *
     * @param orgMember
     * @param search
     * @return
     */
    @Override
    public List<UserPreviewDto> getSearchedOrgMembers(OrgMember orgMember, String search, int page, int size) {

        Long orgId = orgMember.getOrg().getId();
        List<OrgMember> orgMembers = orgMemberRepository.searchOrgMembers(orgId, search, PageRequest.of(page, size)).getContent();
        return orgMembers.stream().map(om -> {
            UserPreviewDto userPreviewDto = getUserPreview(om);
            DirectMessageChat directMessageChat = directMessageChatService.getOrCreateDirectMessageChat(orgMember, om);
            if (directMessageChat != null) {
                userPreviewDto.setChatId(directMessageChat.getPublicId());
            }
            return userPreviewDto;
        }).toList();
    }

    /**
     * Retrieve user's profile as preview
     *
     * @param orgMember
     * @return
     */
    @Override
    public UserPreviewDto getUserPreview(OrgMember orgMember) {

        UserPreviewDto userPreviewDto = UserPreviewDto.fromOrgMember(orgMember);
        userPreviewDto.setDisplayPicture(mediaService.generatePresignedUrl(orgMember.getDisplayPicture()));
        return userPreviewDto;
    }

    /**
     * Generates a token with organization member ID.
     *
     * @param publicUserId
     * @param publicOrgId
     * @return
     */
    @Override
    public String generateTokenWithOrgMemberId(String publicUserId, String publicOrgId) {

        OrgMember orgMember = orgMemberRepository.findByUser_PublicIdAndOrg_PublicId(publicUserId, publicOrgId).orElseThrow(() -> new PermissionDeniedException("You do not have permission to access this organization."));

        if (orgMember.isArchived()) {
            throw new PermissionDeniedException("You are no longer part of this organization.");
        }
        if (!orgMember.getOrg().isActive()) {
            throw new ActionProhibitedException("Subscription for this organization is not active.");
        }

        return authService.generateTokenWithOrgContext(orgMember.getUser().getEmail(), orgMember.getPublicId());
    }

    /**
     * Removes all members from an organization.
     *
     * @param org
     */
    @Override
    public void removeAllOrgMembers(Org org) {

        orgMemberRepository.deleteAllByOrg(org);
    }

    /**
     * Updates an organization member's details.
     *
     * @param orgMemberPublicId
     * @param addOrgMemberDto
     * @param currentOrgMember
     */
    @Override
    public void updateOrgMember(String orgMemberPublicId, AddOrgMemberDto addOrgMemberDto, OrgMember currentOrgMember) {

        OrgMember orgMember = getOrgMemberByPublicId(orgMemberPublicId);

        Org org = currentOrgMember.getOrg();
        if (!orgMember.getOrg().getId().equals(org.getId())) {
            throw new ActionProhibitedException("The member you are trying to update does not exist.");
        }

        if (!orgMember.getPublicId().equals(currentOrgMember.getPublicId()) && !OrgHelper.isOrgOwner(orgMemberPublicId, org)) {
            if (!hasPrivilegeToUpdateMember(org, currentOrgMember.getRole())) {
                throw new PermissionDeniedException("You do not have permission to update members of this organization.");
            }
        }

        if (addOrgMemberDto.getUserName() != null) {
            if (orgMemberRepository.existsByUserNameAndOrg(addOrgMemberDto.getUserName(), org)) {
                throw new ActionProhibitedException("Username " + addOrgMemberDto.getUserName() + " is already taken. Please choose a different username.");
            }
        }

        addOrgMemberDto.applyToOrgMember(orgMember);
        orgMemberRepository.save(orgMember);
    }

}
