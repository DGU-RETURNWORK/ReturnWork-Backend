package com.example.dgu.returnwork.domain.resume.entity;

import com.example.dgu.returnwork.domain.BaseTimeEntity;
import com.example.dgu.returnwork.domain.resume.enums.QuestionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_question_id")
    private Long id;

    @Column(name = "resume_question")
    private String questionTitle;

    @Column(name = "resume_answer", columnDefinition = "text")
    private String answer;

    @Column(name = "word_limit")
    private Integer wordLimit;

    @Column(name = "question_status")
    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.GENERATED;

    @Column(name = "ai_answer", columnDefinition = "text")
    private String aiAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public ResumeQuestion (final String questionTitle,
                           final String answer,
                           final Integer wordLimit,
                           final Resume resume){
        this.questionTitle = questionTitle;
        this.answer = answer;
        this.wordLimit = wordLimit;
        this.resume = resume;
    }

    public static ResumeQuestion create(Resume resume, String questionTitle){
        return ResumeQuestion.builder()
                .resume(resume)
                .questionTitle(questionTitle)
                .build();
    }
}
