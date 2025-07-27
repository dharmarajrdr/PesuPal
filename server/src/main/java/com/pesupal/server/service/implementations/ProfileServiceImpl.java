package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.DirectMessageChatService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.ProfileService;
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
        UserBasicInfoDto userBasicInfoDto = UserBasicInfoDto.fromOrgMember(orgMember);
        DirectMessageChat directMessageChat = directMessageChatService.getOrCreateDirectMessageChat(currentOrgMember, orgMember);
        if(directMessageChat != null)  {
            userBasicInfoDto.setChatId(directMessageChat.getPublicId());
        }
        return userBasicInfoDto;
    }

    private final DirectMessageChatService directMessageChatService;
}
