package com.pesupal.server.repository;

import com.pesupal.server.model.user.User;
import com.pesupal.server.model.user.UserOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserOnboardingRepository extends JpaRepository<UserOnboarding, UUID> {

    boolean existsByUserId(Long id);

    Optional<UserOnboarding> findByUserId(User user);
}
