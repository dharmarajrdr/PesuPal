package com.pesupal.server.repository.module.relation;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordGeoLocationRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordGeoLocationRelationRepository extends JpaRepository<RecordGeoLocationRelation, Long> {

    void deleteAllByRecord_Module(Module recordModule);

    void deleteAllByRecordAndField(ModuleRecord moduleRecord, ModuleField field);

    Optional<RecordGeoLocationRelation> findByRecordAndField(ModuleRecord moduleRecord, ModuleField moduleField);
}
