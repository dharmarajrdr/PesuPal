package com.pesupal.server.repository;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupChatConfigurationRepository extends JpaRepository<GroupChatConfiguration, Long> {

    Optional<GroupChatConfiguration> findByGroupAndRole(Group group, Role role);
}
