package com.pesupal.server.repository.module.relation;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordCurrencyRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordCurrencyRelationRepository extends JpaRepository<RecordCurrencyRelation, Long> {

    void deleteAllByRecord_Module(Module module);

    void deleteAllByRecordAndField(ModuleRecord record, ModuleField field);

    Optional<RecordCurrencyRelation> findByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);
}
