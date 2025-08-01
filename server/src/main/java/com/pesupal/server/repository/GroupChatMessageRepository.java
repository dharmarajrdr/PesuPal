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

    Page<GroupChatMessage> findAllByGroup_PublicIdAndIdLessThan(String groupId, Long pivotMessageId, Pageable pageable);

    Page<GroupChatMessage> findAllByGroup_PublicId(String groupId, Pageable pageable);

    Optional<GroupChatMessage> findFirstByGroupOrderByCreatedAtDesc(Group group);
}
