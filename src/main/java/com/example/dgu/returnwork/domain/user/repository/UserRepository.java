package com.example.dgu.returnwork.domain.user.repository;


import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.domain.user.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderId(String providerId);
    List<User> findByStatusAndDeletedAtBefore(Status status, LocalDateTime deletedAt);

}
