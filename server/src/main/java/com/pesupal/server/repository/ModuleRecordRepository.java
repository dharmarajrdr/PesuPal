package com.pesupal.server.repository;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.projections.PublicIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRecordRepository extends JpaRepository<ModuleRecord, Long> {

    boolean existsByModuleAndSubject(Module module, String subject);

    Optional<ModuleRecord> findByPublicId(String recordId);

    void deleteAllByModule(Module module);

    Page<PublicIdProjection> findAllPublicIdByModule_PublicId(String moduleId, PageRequest pageRequest);
}
