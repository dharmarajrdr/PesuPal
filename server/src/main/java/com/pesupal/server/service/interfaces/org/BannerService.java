package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.CreateBannerDto;
import com.pesupal.server.model.org.Banner;

public interface BannerService {

    Banner getBannerById(Long bannerId);

    Banner createBanner(CreateBannerDto createBannerDto);

    void deleteBanner(Long bannerId);
}
