package com.example.dgu.returnwork.domain.possibility.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NcsCatalog {
    private final List<NcsItem> all;

    public NcsCatalog(ObjectMapper om) throws IOException {
        try (var is = getClass().getResourceAsStream("/allowed_ncs_v5.json")) {
            this.all = Arrays.asList(om.readValue(is, NcsItem[].class));
        }
    }

    public List<Map<String, String>> sampleDeterministic(String seedKey, int limit) {
        List<NcsItem> copy = new ArrayList<>(all);
        Collections.shuffle(copy, new Random(seedKey.hashCode()));
        List<Map<String, String>> out = new ArrayList<>(Math.min(limit, copy.size()));
        for (int i = 0; i < Math.min(limit, copy.size()); i++) {
            NcsItem n = copy.get(i);
            out.add(Map.of("code", n.code(), "name", n.name()));
        }
        return out;
    }

    public record NcsItem(String code, String name, int level) {}
}
