package com.pesupal.server.repository;

import com.pesupal.server.model.module.relation.RecordSelectRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordSelectRelationRepository extends JpaRepository<RecordSelectRelation, Long> {
}
