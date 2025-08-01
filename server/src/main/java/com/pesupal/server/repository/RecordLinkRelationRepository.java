package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordLinkRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordLinkRelationRepository extends JpaRepository<RecordLinkRelation, Long> {

    void deleteAllByRecord_Module(Module recordModule);

    void deleteAllByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);

    Optional<RecordLinkRelation> findByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);
}
