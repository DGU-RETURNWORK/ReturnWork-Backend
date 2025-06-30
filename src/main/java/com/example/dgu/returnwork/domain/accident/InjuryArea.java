package com.example.dgu.returnwork.domain.accident;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InjuryArea {
    HEAD("001", "머리"),
    EYE("002", "눈"),
    NECK("003", "목"),
    SHOULDER("004", "어깨"),
    ARM("005", "팔"),
    HAND("006", "손"),
    FINGER("007", "손가락"),
    BACK("008", "등"),
    SPINE("009", "척추"),
    TORSO("010", "몸통"),
    LEG("011", "다리"),
    FOOT("012", "발"),
    TOE("013", "발가락"),
    WHOLE_BODY("014", "전신"),
    INTERNAL_ORGANS("015", "신체 내부기관(소화, 신경, 순환, 호흡배설 등");

    private final String code;
    private final String description;

}
