package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.request.CreateDepartmentDto;
import com.pesupal.server.dto.request.CreateDesignationDto;
import com.pesupal.server.dto.response.LatestSubscriptionDto;
import com.pesupal.server.dto.response.OrgDetailDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.model.user.Designation;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.OrgMemberRepository;
import com.pesupal.server.service.interfaces.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrgMemberServiceImpl implements OrgMemberService {

    private final OrgService orgService;
    private final UserService userService;
    private final AuthService authService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgConfigurationService orgConfigurationService;
    private final DirectMessageChatService directMessageChatService;
    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    public OrgMemberServiceImpl(OrgService orgService, UserService userService, AuthService authService, @Lazy DepartmentService departmentService, DesignationService designationService, OrgMemberRepository orgMemberRepository, OrgConfigurationService orgConfigurationService, OrgSubscriptionHistoryService orgSubscriptionHistoryService, DirectMessageChatService directMessageChatService) {
        this.orgService = orgService;
        this.userService = userService;
        this.authService = authService;
        this.departmentService = departmentService;
        this.designationService = designationService;
        this.orgMemberRepository = orgMemberRepository;
        this.orgConfigurationService = orgConfigurationService;
        this.orgSubscriptionHistoryService = orgSubscriptionHistoryService;
        this.directMessageChatService = directMessageChatService;
    }

    /**
     * Retrieves an organization member by their public ID.
     *
     * @param publicId
     * @return
     */
    @Override
    public OrgMember getOrgMemberByPublicId(String publicId) {

        return orgMemberRepository.findByPublicId(publicId).orElseThrow(() -> new DataNotFoundException("User with ID " + publicId + " does not exist."));
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
     * Retrieves all members of a department.
     *
     * @param departmentId
     * @return List<UserBasicInfoDto>
     */
    @Override
    public List<UserBasicInfoDto> getAllMembers(Long departmentId, OrgMember orgMember) {

        Department department = departmentService.getDepartmentByIdAndOrg(departmentId, orgMember.getOrg());
        return orgMemberRepository.findAllByOrgAndDepartmentOrderByDisplayName(orgMember.getOrg(), department).stream().map(UserBasicInfoDto::fromOrgMember).toList();
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

    /**
     * Retrieves an organization member by their ID.
     *
     * @param orgMemberId
     * @return OrgMember
     */
    public OrgMember getOrgMemberById(Long orgMemberId) {

        return orgMemberRepository.findById(orgMemberId).orElseThrow(() -> new DataNotFoundException("Org member not found with ID: " + orgMemberId));
    }

    private Designation createDummyDesignationForNewOrg(Org org) {

        CreateDesignationDto createDesignationDto = new CreateDesignationDto();
        createDesignationDto.setName("CEO");
        createDesignationDto.setSeniorityLevel(10L);
        createDesignationDto.setOrgId(org.getId());
        return designationService.createDesignation(createDesignationDto);
    }

    private Department createDummyDepartmentForNewOrg() {

        CreateDepartmentDto createDepartmentDto = new CreateDepartmentDto();
        createDepartmentDto.setHeadId(null);
        createDepartmentDto.setName("Executive Department");
        return departmentService.createDepartment(createDepartmentDto);
    }

    /**
     * Joins an organization as the first member.
     *
     * @param user
     * @return
     */
    @Override
    public OrgMember joinOrgAsFirstMember(User user, Org org) {

        AddOrgMemberDto addOrgMemberDto = new AddOrgMemberDto();
        addOrgMemberDto.setUserId(user.getId()); // Assuming the first user has ID 1
        addOrgMemberDto.setUserName("user_" + user.getId()); // Assuming a default username format
        addOrgMemberDto.setDisplayName("Org Owner");
        addOrgMemberDto.setRole(Role.ADMIN);
        addOrgMemberDto.setDesignationId(createDummyDesignationForNewOrg(org).getId()); // Assuming a default designation
        addOrgMemberDto.setDepartmentId(createDummyDepartmentForNewOrg().getId()); // Assuming a default department
        addOrgMemberDto.setManagerId(null); // Assuming the first member is their own manager
        return addMemberToOrg(addOrgMemberDto, null, true);
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

        OrgMember newOrgMember = new OrgMember();
        newOrgMember.setAddedBy(addedBy);
        newOrgMember.setManager(manager);
        newOrgMember.setOrg(org);
        newOrgMember.setUser(userToAdd);
        newOrgMember.setUserName(addOrgMemberDto.getUserName());
        newOrgMember.setDisplayName(addOrgMemberDto.getDisplayName());
        newOrgMember.setDepartment(departmentService.getDepartmentById(addOrgMemberDto.getDepartmentId()));
        newOrgMember.setDesignation(designationService.getDesignationById(addOrgMemberDto.getDesignationId()));
        newOrgMember.setEmployeeId(countOrgMembersByOrg(org));
        newOrgMember.setArchived(false);
        newOrgMember.setRole(addOrgMemberDto.getRole());
        newOrgMember.setDisplayPicture("https://example.com/default-profile-pic.png"); // Default profile picture URL
        newOrgMember.setStatus("Away");     // Default status
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
            UserBasicInfoDto userBasicInfoDto = UserBasicInfoDto.fromOrgMember(orgMember);
            DirectMessageChat directMessageChat = directMessageChatService.getOrCreateDirectMessageChat(currentOrgMember, orgMember);
            if (directMessageChat != null) {
                userBasicInfoDto.setChatId(directMessageChat.getPublicId());
            }
            return userBasicInfoDto;
        }).toList();
    }

    /**
     * Retrieves the image URL of an organization member by user ID and org ID.
     *
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public String getOrgMemberImageByUserIdAndOrgId(Long userId, Long orgId) {

        return getOrgMemberByUserIdAndOrgId(userId, orgId).getDisplayPicture();
    }

    /**
     * Retrieve user's profile as preview
     *
     * @param orgMember
     * @return
     */
    @Override
    public UserPreviewDto getUserPreview(OrgMember orgMember) {

        return UserPreviewDto.fromOrgMember(orgMember);
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

}
