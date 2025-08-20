package com.example.dgu.returnwork.domain.possibility.dto.request;

import java.util.List;
import java.util.Map;

public record OpenAiRequestDto(
        String model,
        List<Message> input,
        //ResponseFormat responseFormat,
        Integer max_output_tokens
) {
    public record Message(String role, String content) {}
    public record ResponseFormat(String type, JsonSchema jsonSchema) {
        public record JsonSchema(
                String name,
                Map<String, Object> schema,
                Boolean strict
        ) {}
    }
}
