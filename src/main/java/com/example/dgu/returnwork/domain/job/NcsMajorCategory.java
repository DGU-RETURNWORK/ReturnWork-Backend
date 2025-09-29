package com.example.dgu.returnwork.domain.job;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum NcsMajorCategory {
    MANAGEMENT("01", "사업관리", "사업관리", "category/01/hero"),
    BUSINESS_ADMIN("02", "경영·회계·사무", "경영사무", "category/02/hero"),
    FINANCE("03", "금융·보험", "금융보험", "category/03/hero"),
    EDUCATION("04", "교육·자연·사회과학", "교육과학", "category/04/hero"),
    LAW_DEFENSE("05", "법률·경찰·소방·교도·국방", "법률안전", "category/05/hero"),
    HEALTHCARE("06", "보건·의료", "보건의료", "category/06/hero"),
    SOCIAL_WELFARE("07", "사회복지·종교", "사회복지", "category/07/hero"),
    CULTURE_ART("08", "문화·예술·디자인·방송", "문화예술", "category/08/hero"),
    TRANSPORT("09", "운전·운송", "운전운송", "category/09/hero"),
    SALES("10", "영업판매", "영업판매", "category/10/hero"),
    SECURITY_CLEANING("11", "경비·청소", "시설관리", "category/11/hero"),
    HOSPITALITY("12", "이용·숙박·여행·오락·스포츠", "관광레저", "category/12/hero"),
    FOOD_SERVICE("13", "음식서비스", "음식서비스", "category/13/hero"),
    CONSTRUCTION("14", "건설", "건설", "category/14/hero"),
    MACHINERY("15", "기계", "기계", "category/15/hero"),
    MATERIALS("16", "재료", "재료", "category/16/hero"),
    CHEMISTRY_BIO("17", "화학·바이오(구.화학)", "화학바이오", "category/17/hero"),
    TEXTILE("18", "섬유·의복", "섬유의복", "category/18/hero"),
    ELECTRICAL_ELECTRONIC("19", "전기·전자", "전기전자", "category/19/hero"),
    IT("20", "정보통신", "정보통신", "category/20/hero"),
    FOOD_PROCESSING("21", "식품가공", "식품가공", "category/21/hero"),
    PRINT_WOOD_CRAFT("22", "인쇄·목재·가구·공예", "생활제조", "category/22/hero"),
    ENVIRONMENT_ENERGY("23", "환경·에너지·안전", "환경안전", "category/23/hero"),
    AGRICULTURE("24", "농림어업", "농림어업", "category/24/hero");

    private final String prefix;
    private final String officialName;
    private final String displayName;
    private final String assetKey;

    public static NcsMajorCategory getFromJobCode(String jobCode) {
        if (jobCode == null || jobCode.length() < 2) {
            throw new IllegalArgumentException("잘못된 jobCode: " + jobCode);
        }
        String prefix = jobCode.substring(0, 2);
        return Arrays.stream(values())
                .filter(c -> c.prefix.equals(prefix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 jobCode prefix: " + prefix));
    }

}
