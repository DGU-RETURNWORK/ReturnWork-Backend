package com.example.dgu.returnwork.domain.accident.controller;

import com.example.dgu.returnwork.domain.accident.dto.request.CreateAccidentRequestDto;
import com.example.dgu.returnwork.domain.accident.service.AccidentCommandService;
import com.example.dgu.returnwork.domain.user.User;
import com.example.dgu.returnwork.global.annotation.CurrentUser;
import com.example.dgu.returnwork.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ApiResponse<Void> createAccident(User user, CreateAccidentRequestDto request) {
        accidentCommandService.createAccident(user, request);

        return ApiResponse.success(null);
    }

}
