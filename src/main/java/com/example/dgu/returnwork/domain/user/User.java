package com.example.dgu.returnwork.domain.user;

import com.example.dgu.returnwork.domain.BaseTimeEntity;
import com.example.dgu.returnwork.domain.accident.Accident;
import com.example.dgu.returnwork.domain.profile.Profile;
import com.example.dgu.returnwork.domain.region.Region;
import com.example.dgu.returnwork.domain.resume.Resume;
import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.user.enums.Provider;
import com.example.dgu.returnwork.domain.user.enums.Role;
import com.example.dgu.returnwork.domain.user.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"password", "profiles", "resumes", "accidents", "surveys"})
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id = UUID.randomUUID();

    @Column(name= "name", length = 15)
    private String name;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "career", columnDefinition = "TEXT")
    private String career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Provider provider = Provider.NORMAL;

    @Column(name = "provider_id", length = 100)
    private String providerId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // == 양방향 관계 + CASCADE 설정 == //
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Profile> profiles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Resume> resumes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Accident> accidents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Survey> surveys = new ArrayList<>();


    //== equals/hashCode 문제 == //
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email);
    }

    // == delete 메서드 == //
    public void softDelete(){
        this.status = Status.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    // === 복구 메서드 == //
    public void restore(){
        this.status = Status.ACTIVE;
        this.deletedAt = null;
    }

    // == update 메서드 == //
    public void update(String name, String phoneNumber, LocalDate birthDay, Region region, String career) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthDay;
        this.region = region;
        this.career = career;
        this.status = Status.ACTIVE;
    }

}
