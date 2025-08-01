package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordStringRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordStringRelationRepository extends JpaRepository<RecordStringRelation, Long> {

    Optional<RecordStringRelation> findByRecordAndField(ModuleRecord record, ModuleField field);

    void deleteAllByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);

    void deleteAllByRecord_Module(Module module);
}
