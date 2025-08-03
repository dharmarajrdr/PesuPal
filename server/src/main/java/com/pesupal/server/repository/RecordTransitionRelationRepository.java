package com.pesupal.server.repository;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordTransitionRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordTransitionRelationRepository extends JpaRepository<RecordTransitionRelation, Long> {

    void deleteAllByRecord_Module(Module module);

    Optional<RecordTransitionRelation> findByRecordAndField(ModuleRecord record, ModuleField field);

    void deleteAllByRecordAndField(ModuleRecord record, ModuleField field);

    Optional<RecordTransitionRelation> findByField_ModuleAndField_FieldType(Module module, FieldType fieldType);
}
