package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.response.UserBasicInfoDto;

public interface ProfileService {

    UserBasicInfoDto getOrgMemberBasicInfoByUserIdAndOrgId(String userId);
}
