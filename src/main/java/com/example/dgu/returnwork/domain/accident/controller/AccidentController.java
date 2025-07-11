package com.example.dgu.returnwork.domain.accident.controller;

import com.example.dgu.returnwork.domain.accident.dto.request.CreateAccidentRequestDto;
import com.example.dgu.returnwork.domain.accident.service.AccidentCommandService;
import com.example.dgu.returnwork.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/possibility/accident")
public class AccidentController implements AccidentApi {

    private final AccidentCommandService accidentCommandService;

    @PostMapping
    public ApiResponse<Void> createAccident(@RequestBody @Valid CreateAccidentRequestDto request) {
        UUID mockUserId = UUID.fromString("6a7dad08-761c-468d-a867-5f4a52c2e455");

        accidentCommandService.createAccident(mockUserId, request);

        return ApiResponse.success(null);
    }

}
