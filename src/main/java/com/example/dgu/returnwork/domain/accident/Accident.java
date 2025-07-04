package com.example.dgu.returnwork.domain.accident;

import com.example.dgu.returnwork.domain.BaseTimeEntity;
import com.example.dgu.returnwork.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "accident")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Accident extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accident_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "industry_type", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private IndustryType industryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accident_type", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private AccidentType accidentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "injury_area", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private InjuryArea injuryArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "injury_severity", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private InjurySeverity injurySeverity;

    @Column(name = "detail", nullable = false, length = 200)
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
