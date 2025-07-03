package com.example.dgu.returnwork.domain.resume;

import com.example.dgu.returnwork.domain.BaseTimeEntity;
import com.example.dgu.returnwork.domain.job.Job;
import com.example.dgu.returnwork.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "capability", nullable = false, length = 50)
    private String capability;

    @Column(name = "career", columnDefinition = "text")
    private String career;

    @Column(name = "question", columnDefinition = "text")
    private String question;

    @Column(name = "prompt", columnDefinition = "text")
    private String prompt;

    @Column(name = "resume", columnDefinition = "text")
    private String resume;

    @Column(name = "word_limit", nullable = false)
    private Integer wordLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
}