package com.example.dgu.returnwork.domain.resume.entity;

import com.example.dgu.returnwork.domain.resume.enums.QuestionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_question_id")
    private Long id;

    @Column(name = "resume_question")
    private String questionTitle;

    @Column(name = "resume_answer", columnDefinition = "text")
    private String answer;

    @Column(name = "question_order")
    private Integer questionOrder;

    @Column(name = "word_limit")
    private Integer wordLimit;

    @Column(name = "prompt", columnDefinition = "text")
    private String prompt;

    @Column(name = "question_staus")
    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.GENERATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public ResumeQuestion (final String questionTitle,
                           final String answer,
                           final Integer questionOrder,
                           final Integer wordLimit,
                           final String prompt,
                           final Resume resume){
        this.questionTitle = questionTitle;
        this.answer = answer;
        this.questionOrder = questionOrder;
        this.wordLimit = wordLimit;
        this.prompt = prompt;
        this.resume = resume;
    }

    public static ResumeQuestion create(Integer questionOrder,
                                        Resume resume){
        return ResumeQuestion.builder()
                .questionOrder(questionOrder)
                .resume(resume)
                .build();
    }

}
