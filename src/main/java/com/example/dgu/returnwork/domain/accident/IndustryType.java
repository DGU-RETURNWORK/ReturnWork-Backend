package com.example.dgu.returnwork.domain.accident;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IndustryType {
    AGRICULTURE("A", "농업, 임업 및 어업"),
    MINING("B", "광업"),
    MANUFACTURING("C", "제조업"),
    ELECTRICITY("D", "전기, 가스, 증기 및 공기조절 공급업"),
    ENVIRONMENT("E", "수도, 하수 및 폐기물 처리, 원료 재생업"),
    CONSTRUCTION("F", "건설업"),
    RETAIL("G", "도매 및 소매업"),
    TRANSPORT("H", "운수 및 창고업"),
    ACCOMMODATION("I", "숙박 및 음식점업"),
    IT("J", "정보통신업"),
    FINANCE("K", "금융 및 보험업"),
    REAL_ESTATE("L", "부동산업"),
    PROFESSIONAL("M", "전문, 과학 및 기술 서비스업"),
    SUPPORT("N", "사업시설 관리, 사업 지원 및 임대 서비스업"),
    PUBLIC_ADMIN("O", "공공행정, 국방 및 사회보장 행정"),
    EDUCATION("P", "교육 서비스업"),
    HEALTH("Q", "보건업 및 사회복지 서비스업"),
    CULTURE("R", "예술, 스포츠 및 여가관련 서비스업"),
    PERSONAL("S", "협회 및 단체, 수리 및 기타 개인 서비스업"),
    HOUSEHOLD("T", "가구 내 고용활동 및 달리 분류되지 않은 자가소비 생산활동"),
    INTERNATIONAL("U", "국제 및 외국기관");

    private final String code;
    private final String displayName;
}
