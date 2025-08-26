package com.example.dgu.returnwork.domain.survey.meta;

import java.util.List;

public final class SurveyMetaRegistry {
    private SurveyMetaRegistry() {}
    public static final int VERSION = 1;

    public static final List<SurveyQuestionMeta> Q = List.of(
            new SurveyQuestionMeta(1, "작업에 집중을 유지할 수 있는 시간은 어느 정도인가요?",
                    "집중 지속시간", "attention", "cognitive", 1, 5, 1.1),
            new SurveyQuestionMeta(2, "새로운 작업 절차를 배울 때 이해 속도는 어떤가요?",
                    "학습 속도", "learning", "cognitive", 1, 5, 1.0),
            new SurveyQuestionMeta(3, "하루에 몇 kg까지 반복적으로 들 수 있습니까?",
                    "반복 들기(kg)", "strength", "physical", 1, 5, 1.2),
            new SurveyQuestionMeta(4, "손가락을 이용한 세밀한 작업(나사 조립, 글씨쓰기 등)을 얼마나 잘 수행할 수 있습니까?",
                    "손 정밀도", "fine_motor", "motor", 1, 5, 1.3),
            new SurveyQuestionMeta(5, "다른 사람과 협력하여 작업할 때 불편함은 어느 정도입니까?",
                    "협업/사회성", "social", "social", 1, 5, 0.8),
            new SurveyQuestionMeta(6, "최근 기억(방금 들은 지시 등)을 유지하는 데 어려움이 있나요?",
                    "기억력", "memory", "cognitive", 1, 5, 1.0),
            new SurveyQuestionMeta(7, "서 있거나 걷는 동안 균형을 유지하는 데 어려움이 있나요?",
                    "균형/자세유지", "balance", "motor", 1, 5, 1.0),
            new SurveyQuestionMeta(8, "소음, 빛, 반복 동작 등 작업 환경에서 스트레스를 견디는 정도는 어떻습니까?",
                    "작업환경 적응", "tolerance", "environment", 1, 5, 0.9),
            new SurveyQuestionMeta(9, "하루에 무리 없이 일할 수 있는 시간은 어느 정도인가요?",
                    "일 가능 시간", "stamina", "physical", 1, 5, 1.0),
            new SurveyQuestionMeta(10, "사고 이후 본인의 자신감/사회적 활동 의지가 어느 정도입니까?",
                    "동기/자신감", "motivation", "psych", 1, 5, 0.8)
    );
}
