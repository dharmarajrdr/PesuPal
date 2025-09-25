package com.pesupal.server.repository.chat.group_message;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatConfigurationRepository extends JpaRepository<GroupChatConfiguration, Long> {

    Optional<GroupChatConfiguration> findByGroupAndRole(Group group, Role role);

    List<GroupChatConfiguration> findAllByGroup(Group group);
}
