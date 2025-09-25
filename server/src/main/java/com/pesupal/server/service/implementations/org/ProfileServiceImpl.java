package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageChatService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.service.interfaces.org.ProfileService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl extends CurrentValueRetriever implements ProfileService {

    private final OrgMemberService orgMemberService;

    /**
     * Retrieves basic information of an organization member by user ID and org ID.
     *
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public UserBasicInfoDto getOrgMemberBasicInfoByUserIdAndOrgId(String userId) {

        OrgMember currentOrgMember = getCurrentOrgMember();
        OrgMember orgMember = orgMemberService.getOrgMemberByPublicId(userId);
        UserBasicInfoDto userBasicInfoDto = orgMemberService.getUserBasicInfo(orgMember);
        DirectMessageChat directMessageChat = directMessageChatService.getOrCreateDirectMessageChat(currentOrgMember, orgMember);
        if (directMessageChat != null) {
            userBasicInfoDto.setChatId(directMessageChat.getPublicId());
        }
        return userBasicInfoDto;
    }

    private final DirectMessageChatService directMessageChatService;
}
