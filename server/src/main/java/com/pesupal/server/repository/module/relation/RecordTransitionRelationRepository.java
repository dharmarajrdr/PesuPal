package com.pesupal.server.repository.module.relation;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.Transition;
import com.pesupal.server.model.module.relation.RecordTransitionRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordTransitionRelationRepository extends JpaRepository<RecordTransitionRelation, Long> {

    void deleteAllByRecord_Module(Module module);

    Optional<RecordTransitionRelation> findByRecordAndField(ModuleRecord record, ModuleField field);

    void deleteAllByRecordAndField(ModuleRecord record, ModuleField field);

    Page<RecordTransitionRelation> findAllByTransition(Transition transition, Pageable pageable);
}
