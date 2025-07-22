package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupChatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatMemberRepository extends JpaRepository<GroupChatMember, Long> {
}
