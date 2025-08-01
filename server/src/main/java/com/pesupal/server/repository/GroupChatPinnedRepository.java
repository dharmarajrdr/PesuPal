package com.pesupal.server.repository;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatPinnedRepository extends JpaRepository<GroupChatPinned, Long> {

    Optional<GroupChatPinned> findByPinnedByAndGroup(OrgMember pinnedBy, Group group);

    List<GroupChatPinned> findAllByPinnedByIdAndGroup_Org_IdOrderByOrderIndexAsc(Long userId, Long orgId);

    boolean existsByPinnedByIdAndGroupId(Long pinnedById, Long groupId);

    boolean existsByPinnedByIdAndGroup_PublicId(Long pinnedById, String groupId);

    Optional<GroupChatPinned> findByIdAndPinnedBy(Long id, OrgMember pinnedBy);
}
