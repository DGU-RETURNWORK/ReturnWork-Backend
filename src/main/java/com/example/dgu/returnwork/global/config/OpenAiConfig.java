package com.example.dgu.returnwork.global.config;

import com.example.dgu.returnwork.domain.possibility.dto.request.OpenAiRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
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
            //request.getHeaders().add("Content-Type", "application/json");
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Bean(name = "returnWorkResultSchema")
    public Map<String, Object> returnWorkResultSchema() {
        return Map.of(
                "type", "object",
                "additionalProperties", false,
                "required", List.of("jobSummaries", "capabilities"),
                "properties", Map.of(
                        "jobSummaries", Map.of(
                                "type", "array",
                                "items", Map.of(
                                        "type", "object",
                                        "additionalProperties", false,
                                        "required", List.of("jobName", "jobFitness", "jobCode"),
                                        "properties", Map.of(
                                                "jobName", Map.of("type", "string"),
                                                "jobFitness", Map.of("type", "integer", "minimum", 0, "maximum", 100),
                                                "jobCode", Map.of("type", "string")
                                        )
                                )
                        ),
                        "capabilities", Map.of(
                                "type", "array",
                                "items", Map.of("type", "string")
                        )
                )
        );
    }

    @Bean
    public OpenAiRequestDto.ResponseFormat responseFormat(
            @Qualifier("returnWorkResultSchema") Map<String, Object> schema) {
        return new OpenAiRequestDto.ResponseFormat(
                "json_schema",
                new OpenAiRequestDto.ResponseFormat.JsonSchema(
                        "ReturnWorkResult",
                        schema,
                        true
                )
        );
    }
}
