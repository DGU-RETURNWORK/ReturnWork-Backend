package com.example.dgu.returnwork.domain.job.repository;

import com.example.dgu.returnwork.domain.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job , Long> {

    Optional<Job> findJobByName(String name);
}
