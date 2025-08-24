package com.example.dgu.returnwork.domain.survey.service;

import com.example.dgu.returnwork.domain.survey.Survey;
import com.example.dgu.returnwork.domain.survey.meta.SurveyMetaRegistry;
import com.example.dgu.returnwork.domain.survey.meta.SurveyQuestionMeta;
import com.example.dgu.returnwork.domain.survey.service.payload.SurveyPayload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class SurveyPayloadBuilder {

    public SurveyPayload build(Survey survey) {
        List<SurveyQuestionMeta> metas = SurveyMetaRegistry.Q;

        Map<String, Double> domainSum = new HashMap<>();
        Map<String, Double> domainWeight = new HashMap<>();
        Map<Integer, Map<String, Object>> qMap = new LinkedHashMap<>();

        for (var m : metas) {
            int id = m.id();
            double w = m.weight();
            String domain = m.domain();

            int ans = getAnswerOrDefault(survey, id, 1);

            domainSum.merge(domain, ans * w, Double::sum);
            domainWeight.merge(domain, w, Double::sum);

            qMap.put(id, Map.of(
                    "key", m.metricKey(),
                    "domain", domain,
                    "weight", w,
                    "min", m.min(),
                    "max", m.max(),
                    "answer", ans
            ));
        }

        Map<String, Double> scores = new LinkedHashMap<>();
        for (var e : domainSum.entrySet()) {
            String d = e.getKey();
            double wSum = domainWeight.getOrDefault(d, 0.0);
            scores.put(d, wSum == 0 ? 0.0 : e.getValue() / wSum);
        }

        return new SurveyPayload(SurveyMetaRegistry.VERSION, scores, qMap);

    }

    private int getAnswerOrDefault(Survey s, int qid, int defaultVal) {
        return switch (qid) {
            case 1 -> s.getA1();
            case 2 -> s.getA2();
            case 3 -> s.getA3();
            case 4 -> s.getA4();
            case 5 -> s.getA5();
            case 6 -> s.getA6();
            case 7 -> s.getA7();
            case 8 -> s.getA8();
            case 9 -> s.getA9();
            case 10 -> s.getA10();
            default -> defaultVal;
        };
    }
}
