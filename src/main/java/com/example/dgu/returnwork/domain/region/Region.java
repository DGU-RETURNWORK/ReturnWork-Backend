package com.example.dgu.returnwork.domain.region;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "regions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String fullAddress;

    @Column(length = 50)
    private String sido;

    @Column(length = 50)
    private String sigungu;

    @Column(length = 50)
    private String dongLi;

    @Column(length = 50)
    private String sidoNormalized;

    @Column(length = 500)
    private String searchKeywords;


}
