package com.example.dgu.returnwork.domain.possibility.controller;

import com.example.dgu.returnwork.domain.possibility.dto.request.GetPossibilityRequestDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetLLMResponseDto;
import com.example.dgu.returnwork.domain.possibility.dto.response.GetPossibilityAndJobResponseDto;
import com.example.dgu.returnwork.domain.possibility.service.PossibilityCommandService;
import com.example.dgu.returnwork.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/possibility")
public class PossibilityController implements PossibilityApi{

    private final PossibilityCommandService possibilityCommandService;

    @PostMapping("/analyses")
    public GetPossibilityAndJobResponseDto getPossibility(User user, GetPossibilityRequestDto request) {
        return possibilityCommandService.getPossibility(user, request);
    }

}
