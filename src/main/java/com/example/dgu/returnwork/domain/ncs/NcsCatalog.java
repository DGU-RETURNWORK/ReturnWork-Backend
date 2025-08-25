package com.example.dgu.returnwork.domain.ncs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NcsCatalog {
    private final List<AllowedNcsSampler.NcsItem> all;

    public NcsCatalog(ObjectMapper om) throws IOException {
        try (var is = getClass().getResourceAsStream("/allowed_ncs_v5.json")) {
            this.all = Arrays.asList(om.readValue(is, AllowedNcsSampler.NcsItem[].class));
        }
    }

    public List<AllowedNcsSampler.NcsItem> getAll() {
        return all;
    }

}
