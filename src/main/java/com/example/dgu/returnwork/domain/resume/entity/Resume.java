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

    @Column(name ="question_count", nullable = false)
    private int questionCount = 1;

    @Column(name = "resume_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResumeStatus resumeStatus =  ResumeStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ResumeQuestion> resumeQuestions = new ArrayList<>();

    @Builder
    public Resume(final String title,
                  final String capability,
                  final String career,
                  final int questionCount,
                  final ResumeStatus resumeStatus,
                  final User user,
                  final Job job){
        this.title = title;
        this.capability = capability;
        this.career = career;
        this.questionCount = questionCount;
        this.resumeStatus = resumeStatus;
        this.user = user;
        this.job = job;
    }


    public static Resume create(String title,
                                String capability,
                                String career,
                                int questionCount,
                                User user,
                                Job job){
        return Resume.builder()
                .title(title)
                .capability(capability)
                .career(career)
                .questionCount(questionCount)
                .resumeStatus(ResumeStatus.DRAFT)
                .user(user)
                .job(job)
                .build();
    }

    public void createResumeQuestion(ResumeQuestion resumeQuestion){
        this.resumeQuestions.add(resumeQuestion);
    }
}