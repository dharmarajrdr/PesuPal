package com.pesupal.server.repository;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long> {

    void deleteAllByGroup(Group group);

    Optional<GroupChatMessage> findFirstByGroupOrderByCreatedAtDesc(Group group);

    Page<GroupChatMessage> findAllByGroupPublicIdAndIdLessThan(String groupPublicId, Long pivotMessageId, Pageable pageable);

    Page<GroupChatMessage> findAllByGroupPublicId(Long pivotMessageId, Pageable pageable);
}
