package com.example.dgu.returnwork.domain.resume.repository;

import com.example.dgu.returnwork.domain.resume.entity.ResumeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ResumeQuestionRepository extends JpaRepository<ResumeQuestion, Long> {

    Optional<ResumeQuestion> findByResumeIdAndId(Long resumeId, Long resumeQuestionId);

    @Query("SELECT rq FROM ResumeQuestion rq JOIN rq.resume r WHERE rq.id = :questionId AND r.id = :resumeId AND r.user.id = :userId")
    Optional<ResumeQuestion> findByIdAndResumeIdAndResumeUserId(
            @Param("questionId") Long questionId,
            @Param("resumeId") Long resumeId,
            @Param("userId") UUID userId
    );
}
