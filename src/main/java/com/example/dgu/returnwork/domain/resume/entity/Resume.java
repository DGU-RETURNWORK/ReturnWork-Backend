package com.example.dgu.returnwork.domain.resume.entity;

import com.example.dgu.returnwork.domain.BaseTimeEntity;
import com.example.dgu.returnwork.domain.job.Job;
import com.example.dgu.returnwork.domain.resume.enums.ResumeStatus;
import com.example.dgu.returnwork.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "resume")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Resume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "capability", nullable = false, length = 50)
    private String capability;

    @Column(name = "career", columnDefinition = "text")
    private String career;

    @Column(name = "word_limit", nullable = false)
    private Integer wordLimit;

    @Column(name ="question_count", nullable = false)
    @Builder.Default
    private Integer questionCount = 1;

    @Column(name = "resume_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ResumeStatus resumeStatus =  ResumeStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ResumeQuestion> resumeQuestions = new ArrayList<>();
}