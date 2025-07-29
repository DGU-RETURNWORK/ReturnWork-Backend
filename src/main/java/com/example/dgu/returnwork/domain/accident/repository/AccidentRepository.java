package com.example.dgu.returnwork.domain.accident.repository;

import com.example.dgu.returnwork.domain.accident.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident, Long> {
}
