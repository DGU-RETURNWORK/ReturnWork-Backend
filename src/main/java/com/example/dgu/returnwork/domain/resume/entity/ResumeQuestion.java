package com.example.dgu.returnwork.domain.resume.entity;

import com.example.dgu.returnwork.domain.resume.enums.QuestionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_question_id")
    private Long id;

    @Column(name = "resume_question", nullable = false)
    private String question;

    @Column(name = "resume_answer", columnDefinition = "text")
    private String answer;

    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;

    @Column(name = "word_limit")
    private Integer wordLimit;

    @Column(name = "prompt", columnDefinition = "text")
    private String prompt;

    @Column(name = "question_staus", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private QuestionStatus questionStatus = QuestionStatus.GENERATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

}
