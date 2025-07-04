package com.example.dgu.returnwork.domain.accident;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccidentType {
    FALL("001", "떨어짐(높이가 있는 곳에서 사람이 떨어짐)"),
    SLIP("002", "넘어짐(사람이 미끄러지거나 넘어짐)"),
    CRUSHED("003", "깔림(물체의 쓰러짐이나 뒤집힘)"),
    COLLISION("004", "부딪힘(물체에 부딪힘)"),
    STRUCK("005", "맞음(날아오거나 떨어진 물체에 맞음)"),
    COLLAPSE("006", "무너짐(건축물이나 쌓여진 물체가 무너짐)"),
    ENTANGLED("007", "끼임(기계설비에 끼이거나 감김)"),
    CUT_OR_STABBED("008", "절단·베임·찔림"),
    ELECTRIC_SHOCK("009", "감전"),
    EXPLOSION("010", "폭발·파열"),
    FIRE("011", "화재"),
    IMBALANCE_OR_OVEREXERTION("012", "불균형 및 무리한 동작"),
    EXTREME_TEMP_OR_CONTACT("013", "이상온도·물체접촉"),
    CHEMICAL_EXPOSURE("014", "화학물질누출·접촉"),
    OXYGEN_DEFICIENCY("015", "산소결핍"),
    DROWNING("016", "빠짐·익사"),
    TRAFFIC_ACCIDENT_INSIDE("017", "사업장내 교통사고"),
    TRAFFIC_ACCIDENT_OUTSIDE("018", "사업장외 교통사고"),
    MARINE_AIR_TRAFFIC("019", "해상항공 교통사고"),
    ATHLETIC_EVENT("020", "체육행사 등의 사고"),
    VIOLENCE("021", "폭력행위"),
    ANIMAL_INJURY("022", "동물상해"),
    OTHER("023", "기타"),
    PHYSICAL_AGENT("024", "물리적인자"),
    ORGANIC_COMPOUND("025", "유기화합물"),
    PERMITTED_SUBSTANCE("026", "허가대상"),
    METAL("027", "금속류"),
    OTHER_CHEMICAL_AGENT("028", "화학적인자 기타"),
    BIO_AGENT("029", "생물학적인자"),
    TOXIC_HEPATITIS("030", "독성간염"),
    OCCUPATIONAL_CANCER("031", "직업성암"),
    OCCUPATIONAL_DISEASE_OTHER("032", "직업병기타"),
    PNEUMOCONIOSIS("033", "진폐"),
    CEREBROVASCULAR("034", "뇌혈관질환"),
    CARDIOVASCULAR("035", "심장질환"),
    LBP_NON_ACCIDENT("036", "비사고성·작업관련성요통"),
    LBP_ACCIDENT("037", "사고성요통"),
    CTS("038", "수근관증후군"),
    PHYSICAL_OVERLOAD("039", "신체에 과도한 부담을 주는 작업"),
    LIVER_DISEASE("040", "간질환"),
    MENTAL_ILLNESS("041", "정신질환"),
    WORK_RELATED_DISEASE_OTHER("042", "작업관련성질병 기타"),
    UNKNOWN("043", "분류불능");

    private final String code;
    private final String description;
}
