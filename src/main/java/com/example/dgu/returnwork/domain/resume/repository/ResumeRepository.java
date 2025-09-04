package com.example.dgu.returnwork.domain.resume.repository;

import com.example.dgu.returnwork.domain.resume.entity.Resume;
import com.example.dgu.returnwork.domain.resume.enums.ResumeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByIdAndUserId(Long resumeId, UUID userId);
    List<Resume> findByUserIdAndResumeStatus(UUID userId, ResumeStatus resumeStatus);
}
