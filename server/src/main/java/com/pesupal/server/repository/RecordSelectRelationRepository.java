package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordSelectRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordSelectRelationRepository extends JpaRepository<RecordSelectRelation, Long> {

    List<RecordSelectRelation> findAllByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);

    void deleteAllByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);

    void deleteAllByRecord_Module(Module module);
}
