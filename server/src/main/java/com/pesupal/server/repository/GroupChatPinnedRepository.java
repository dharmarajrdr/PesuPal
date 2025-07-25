package com.pesupal.server.repository;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatPinnedRepository extends JpaRepository<GroupChatPinned, Long> {

    Optional<GroupChatPinned> findByPinnedByAndGroup(User pinnedBy, Group group);

    List<GroupChatPinned> findAllByPinnedByIdAndGroup_Org_IdOrderByOrderIndexAsc(Long userId, Long orgId);
}
