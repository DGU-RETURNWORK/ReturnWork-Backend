package com.example.dgu.returnwork.domain.user.repository;


import com.example.dgu.returnwork.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);
}
