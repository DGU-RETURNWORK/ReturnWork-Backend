package com.example.dgu.returnwork.infrastructure.ai.dto.request;

import java.util.List;
import java.util.Map;

public record OpenAiRequestDto(
        String model,
        List<Message> input,
        Text text,
        Integer max_output_tokens
) {
    public record Message(String role, String content) {}
    public record Text(Format format) {}
    public record Format(String type,
                         String name,
                         Map<String, Object> schema,
                         Boolean strict
                         ) {}
}
