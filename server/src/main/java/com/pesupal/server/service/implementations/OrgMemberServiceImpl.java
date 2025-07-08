package com.pesupal.server.service.implementations;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.OrgMemberRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrgMemberServiceImpl implements OrgMemberService {

    private final OrgMemberRepository orgMemberRepository;

    /**
     * Retrieves an organization member by user and organization.
     *
     * @param user
     * @param org
     * @return OrgMember
     */
    @Override
    public OrgMember getOrgMemberByUserAndOrg(User user, Org org) {

        return orgMemberRepository.findByUserAndOrg(user, org).orElseThrow(() -> new DataNotFoundException("Org member not found for user and org"));
    }
}
