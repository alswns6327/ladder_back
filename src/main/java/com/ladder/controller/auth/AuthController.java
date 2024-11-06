package com.ladder.controller.auth;

import com.ladder.dto.auth.*;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     * @param RequestRegistDto
     * @return ResponseEntity<ResultDto<ResponseRegistDto>>
     */
    @PostMapping("/account")
    public ResponseEntity<ResultDto<ResponseRegistDto>> registAccount(@RequestBody RequestRegistDto requestRegistDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registAccount(requestRegistDto));
    }

    /**
     * 로그인
     * @param RequestLoginDto
     * @return ResponseEntity<ResultDto<ResponseLoginDto>>
     */
    @PostMapping("/login")
    public ResponseEntity<ResultDto<ResponseLoginDto>> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.login(requestLoginDto, response));
    }

    /**
     * 로그아웃
     *
     */
    @PostMapping("/logout")
    public ResponseEntity<ResultDto<String>> login(HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(authService.logout(request, response));
    }

    @GetMapping("/account/list")
    public ResponseEntity<ResultDto<List<ResponseLadderAccountDto>>> searchUsers(){
        return ResponseEntity.ok().body(authService.searchUsers());
    }
}
