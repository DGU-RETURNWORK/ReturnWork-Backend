package com.example.dgu.returnwork.domain.possibility.service;

import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public final class AllowedNcsSampler {

    private static final double HIGH = 3.6;

    private static final Map<String, List<String>> DOMAIN_KWS = Map.of(
        "motor",       List.of("조립","정비","유지보수","조작","가공"),
        "cognitive",   List.of("품질","검사","검수","계측","도면"),
        "physical",    List.of("운반","포장","라인","현장"),
        "environment", List.of("설비","공정","안전","점검"),
        "social",      List.of("고객","팀","지원")
    );

    public static List<Map<String, String>> pickFromNcs(
            List<NcsItem> allNcs,
            Map<String, Double> domainScores,
            int need,
            String seedKey
    ) {
        List<String> keywords = deriveKeywords(domainScores);
        String[] kws = keywords.toArray(new String[0]);

        List<NcsItem> picked = new ArrayList<>();
        for (NcsItem it : allNcs) {
            if (picked.size() >= need) break;
            if (containsAny(it.name, kws)) {
                picked.add(it);
            }
        }

        if (picked.size() < need) {
            List<NcsItem> rest = new ArrayList<>();

            Set<String> chosen = new HashSet<>();
            for (NcsItem p: picked) chosen.add(p.code());

            for (NcsItem it: allNcs) {
                if (!chosen.contains(it.code())) rest.add(it);
            }

            long seed = (seedKey == null) ? 0L : seedKey.hashCode();
            Collections.shuffle(rest, new Random(seed));

            int remain = Math.min(need - rest.size(), rest.size());
            for (int i = 0; i < remain; i++) picked.add(rest.get(i));
        }

        List<Map<String, String>> out = new ArrayList<>(picked.size());
        for (NcsItem it : picked) {
            out.add(Map.of("code", it.code(), "name", it.name()));
        }
        return out;
    }

    private static List<String> deriveKeywords(Map<String, Double> ds) {
        List<String> ks = new ArrayList<>();

        if (ds.getOrDefault("motor", 0.0)       >= HIGH) ks.addAll(DOMAIN_KWS.get("motor"));
        if (ds.getOrDefault("cognitive", 0.0)   >= HIGH) ks.addAll(DOMAIN_KWS.get("cognitive"));
        if (ds.getOrDefault("physical", 0.0)    >= HIGH) ks.addAll(DOMAIN_KWS.get("physical"));
        if (ds.getOrDefault("environment", 0.0) >= HIGH) ks.addAll(DOMAIN_KWS.get("environment"));
        if (ds.getOrDefault("social", 0.0)      >= HIGH) ks.addAll(DOMAIN_KWS.get("social"));

        return ks;
    }

    private static boolean containsAny(String text, String[] kws) {
        for (String k: kws) {
            if (text.contains(k)) return true;
        }
        return false;
    }

    public record NcsItem(
            String code,
            String name,
            int level
    ) {}
}
