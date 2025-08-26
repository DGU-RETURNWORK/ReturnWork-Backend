package com.example.dgu.returnwork.infrastructure.ai.dto.response;

import java.util.List;

public record OpenAiResponseDto(
        String id,
        List<OutputItem> output
) {
    public record OutputItem(List<ContentItem> content) {}
    public record ContentItem(String type, String text) {}

    public String unifiedText() {
        if (output == null) return null;
        for (var out: output) {
            if (out.content() == null) continue;
            for (var c: out.content()) {
                if (c.text() != null && !c.text().isBlank()) return c.text();
            }
        }
        return null;
    }
}
