package com.example.dgu.returnwork.domain.survey;

import com.example.dgu.returnwork.domain.BaseTimeEntity;
import com.example.dgu.returnwork.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "survey")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Survey extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @Column(name = "name")
    private LocalDate date;

    @Column(name = "a1")
    private Integer a1;

    @Column(name = "a2")
    private Integer a2;

    @Column(name = "a3")
    private Integer a3;

    @Column(name = "a4")
    private Integer a4;

    @Column(name = "a5")
    private Integer a5;

    @Column(name = "a6")
    private Integer a6;

    @Column(name = "a7")
    private Integer a7;

    @Column(name = "a8")
    private Integer a8;

    @Column(name = "a9")
    private Integer a9;

    @Column(name = "a10")
    private Integer a10;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
