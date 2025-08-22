package com.pesupal.server.repository.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.Transition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransitionRepository extends JpaRepository<Transition, Long> {

    Optional<Transition> findByIdAndField(Long transitionId, ModuleField field);
}
