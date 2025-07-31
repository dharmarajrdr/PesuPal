package com.pesupal.server.repository;

import com.pesupal.server.model.module.relation.RecordUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordUserRelationRepository extends JpaRepository<RecordUserRelation, Long> {
}
