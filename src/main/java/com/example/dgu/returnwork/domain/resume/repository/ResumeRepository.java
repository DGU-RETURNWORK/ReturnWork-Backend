package com.example.dgu.returnwork.domain.resume.repository;

import com.example.dgu.returnwork.domain.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
