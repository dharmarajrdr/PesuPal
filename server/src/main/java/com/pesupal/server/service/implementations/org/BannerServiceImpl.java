package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.CreateBannerDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.org.Banner;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.BannerRepository;
import com.pesupal.server.service.interfaces.org.BannerService;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BannerServiceImpl extends CurrentValueRetriever implements BannerService {

    private final BannerRepository bannerRepository;
    private final OrgConfigurationService orgConfigurationService;

    /**
     * Get banner by id.
     *
     * @param bannerId
     * @return
     */
    @Override
    public Banner getBannerById(Long bannerId) {

        return bannerRepository.findById(bannerId).orElseThrow(() -> new DataNotFoundException("Banner not found with id: " + bannerId));
    }

    /**
     * Create a new banner.
     *
     * @param createBannerDto
     * @return
     */
    @Override
    public Banner createBanner(CreateBannerDto createBannerDto) {

        OrgMember orgMember = getCurrentOrgMember();

        if (!orgConfigurationService.hasPrivilegeTo(OrgAction.CREATE_BANNER, orgMember.getRole())) {
            throw new PermissionDeniedException("You do not have permission to create a banner");
        }

        Banner banner = createBannerDto.toBanner();
        banner.setCreatedBy(orgMember);
        return bannerRepository.save(banner);
    }

    /**
     * Delete banner by id.
     *
     * @param bannerId
     */
    @Override
    public void deleteBanner(Long bannerId) {

        OrgMember orgMember = getCurrentOrgMember();
        Banner banner = getBannerById(bannerId);

        if (!banner.getCreatedBy().getId().equals(orgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to delete this banner");
        }

        if (banner.getEndTime().isBefore(LocalDateTime.now())) {
            throw new ActionProhibitedException("Cannot delete a banner that has already displayed");
        }

        bannerRepository.delete(banner);
    }
}
