package com.pesupal.server.repository;

import com.pesupal.server.model.group.GroupChatConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatConfigurationRepository extends JpaRepository<GroupChatConfiguration, Long> {
}
