package com.example.dgu.returnwork.global.config;

import com.example.dgu.returnwork.infrastructure.ai.dto.request.OpenAiRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Configuration
public class OpenAiConfig {
    @Value("${openai.api.key}")
    private String openAiKey;

    @Bean
    public RestTemplate template() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10_000);
        factory.setReadTimeout(60_000);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) ->  {
            request.getHeaders().add("Authorization", "Bearer " + openAiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    @Bean(name = "returnWorkResultSchema")
    public Map<String, Object> returnWorkResultSchema() {
        return Map.of(
                "type", "object",
                "additionalProperties", false,
                "required", List.of("llmJobSummaries", "capabilities"),
                "properties", Map.of(
                        "llmJobSummaries", Map.of(
                                "type", "array",
                                "minItems", 9,
                                "maxItems", 9,
                                "items", Map.of(
                                        "type", "object",
                                        "additionalProperties", false,
                                        "required", List.of("jobName", "jobFitness", "jobCode", "description"),
                                        "properties", Map.of(
                                                "jobName", Map.of("type", "string"),
                                                "jobFitness", Map.of("type", "integer", "minimum", 0, "maximum", 100),
                                                "jobCode", Map.of("type", "string"),
                                                "description", Map.of("type", "string")
                                        )
                                )
                        ),
                        "capabilities", Map.of(
                                "type", "array",
                                "minItems", 20,
                                "maxItems", 20,
                                "items", Map.of("type", "string")
                        )
                )
        );
    }

    @Bean
    public OpenAiRequestDto.Format responseFormat(
            @Qualifier("returnWorkResultSchema") Map<String, Object> schema) {
        return new OpenAiRequestDto.Format(
                "json_schema",
                "PossibilityResult",
                schema,
                true
        );
    }

    @Bean
    public OpenAiRequestDto.Text text(OpenAiRequestDto.Format format) {
        return new OpenAiRequestDto.Text(format);
    }
}
