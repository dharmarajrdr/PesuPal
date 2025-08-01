package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordFileRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordFileRelationRepository extends JpaRepository<RecordFileRelation, Long> {

    void deleteAllByRecord_Module(Module module);

    void deleteByRecordAndField(ModuleRecord record, ModuleField field);

    Optional<RecordFileRelation> findByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);
}
