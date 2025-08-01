package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordUserRelation;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.RecordUserRelationRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.module.relation.RecordUserRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecordUserRelationServiceImpl implements RecordUserRelationService {

    private final OrgMemberService orgMemberService;
    private final RecordUserRelationRepository recordUserRelationRepository;

    /**
     * Saves the data for the user relation record.
     *
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        String userId = (String) data;
        OrgMember orgMember = orgMemberService.getOrgMemberByPublicId(userId);

        RecordUserRelation recordUserRelation = new RecordUserRelation();
        recordUserRelation.setRecord(record);
        recordUserRelation.setField(field);
        recordUserRelation.setUser(orgMember);
        recordUserRelationRepository.save(recordUserRelation);
    }
}
