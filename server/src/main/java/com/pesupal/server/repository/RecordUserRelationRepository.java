package com.pesupal.server.repository;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordUserRelationRepository extends JpaRepository<RecordUserRelation, Long> {

    Optional<RecordUserRelation> findByRecordAndField(ModuleRecord record, ModuleField field);
}
