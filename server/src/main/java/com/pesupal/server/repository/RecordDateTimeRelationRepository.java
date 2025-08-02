package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordDateTimeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordDateTimeRelationRepository extends JpaRepository<RecordDateTimeRelation, Long> {

    void deleteByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);

    void deleteAllByRecord_Module(Module module);

    Optional<RecordDateTimeRelation> findByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);
}
