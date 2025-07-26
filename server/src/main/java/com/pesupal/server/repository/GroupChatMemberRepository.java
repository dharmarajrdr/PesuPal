package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupChatMemberRepository extends JpaRepository<GroupChatMember, Long> {

    Optional<GroupChatMember> findByGroupIdAndUserId(Long groupId, Long userId);

    boolean existsByGroupIdAndUserId(Long groupId, Long userId);

    Optional<GroupChatMember> findByGroup_PublicIdAndUser(String groupPublicId, User user);

    boolean existsByGroup_PublicIdAndUser_Id(String groupPublicId, Long orgMemberPublicId);
}
