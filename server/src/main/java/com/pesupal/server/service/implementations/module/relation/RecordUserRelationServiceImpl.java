package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.exceptions.DataNotFoundException;
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

    /**
     * Retrieves the user relation data for a given module record and module field.
     *
     * @param record
     * @param field
     * @return
     */
    @Override
    public RecordUserRelation getRecordSelectRelation(ModuleRecord record, ModuleField field) {

        return recordUserRelationRepository.findByRecordAndField(record, field).orElseThrow(() -> new DataNotFoundException("No user relation found for the given field."));
    }

    /**
     * Retrieves the user relation data for a given module record and module field.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        ModuleFieldDto<UserPreviewDto> moduleFieldDto = ModuleFieldDto.fromModuleField(moduleField);

        RecordUserRelation recordUserRelation = getRecordSelectRelation(moduleRecord, moduleField);
        moduleFieldDto.setData(UserPreviewDto.fromOrgMember(recordUserRelation.getUser()));
        return moduleFieldDto;
    }
}
