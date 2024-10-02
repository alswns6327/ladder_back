package com.ladder.controller.auth;

import com.ladder.dto.auth.RequestRegistDto;
import com.ladder.dto.auth.ResponseRegistDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/account")
    public ResponseEntity<ResultDto<ResponseRegistDto>> registAccount(@RequestBody RequestRegistDto requestRegistDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registAccount(requestRegistDto));
    }

}
