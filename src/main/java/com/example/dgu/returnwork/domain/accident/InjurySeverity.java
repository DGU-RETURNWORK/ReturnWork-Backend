package com.example.dgu.returnwork.domain.accident;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InjurySeverity {
    FRACTURE("001", "골절"),
    AMPUTATION("002", "절단"),
    CONTUSION("003", "타박상"),
    ABRASION("004", "찰과상"),
    BURN("005", "화상"),
    INTOXICATION_OR_SUFFOCATION("006", "중독·질식"),
    ELECTRIC_SHOCK("007", "감전"),
    CONCUSSION("008", "뇌진탕"),
    HYPERTENSION("009", "고혈압"),
    STROKE("010", "뇌졸중"),
    DERMATITIS("011", "피부염"),
    PNEUMOCONIOSIS("012", "진폐"),
    CARPAL_TUNNEL_SYNDROME("013", "수근관증후군"),
    OTHER("999", "기타");

    private final String code;
    private final String description;
}
