package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.UserBasicInfoDto;
import jakarta.transaction.Transactional;

public interface ProfileService {
    
    UserBasicInfoDto getOrgMemberBasicInfoByUserIdAndOrgId(String userId);
}
