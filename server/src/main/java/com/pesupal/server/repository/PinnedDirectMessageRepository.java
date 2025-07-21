package com.pesupal.server.repository;

import com.pesupal.server.model.chat.PinnedDirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PinnedDirectMessageRepository extends JpaRepository<PinnedDirectMessage, Long> {

    List<PinnedDirectMessage> findAllByOrgIdAndPinnedByIdOrderByOrderIndexAscPinnedUser_IdAsc(Long pinnedById, Long orgId);

    boolean existsByPinnedByIdAndPinnedUserIdAndOrgId(Long pinnedById, Long pinnedUserId, Long orgId);

    Optional<PinnedDirectMessage> findByPinnedByIdAndPinnedUserIdAndOrgId(Long pinnedById, Long pinnedUserId, Long orgId);
}
