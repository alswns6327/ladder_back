package com.ladder.service.auth;

import com.ladder.config.jwt.TokenProvider;
import com.ladder.domain.auth.LadderAccount;
import com.ladder.domain.auth.RefreshToken;
import com.ladder.dto.auth.*;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.auth.AuthRepository;
import com.ladder.repository.auth.RefreshTokenRepository;
import com.ladder.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    public ResultDto<ResponseRegistDto> registAccount(RequestRegistDto requestRegistDto) {
        try{
            LadderAccount ladderAccount = authRepository.save(new LadderAccount(requestRegistDto.encryptPassword(bCryptPasswordEncoder)));
            return ResultDto.of("success", "200", new ResponseRegistDto(ladderAccount.getLadderAccountId()));
        }catch (Exception e){
            return ResultDto.of("fail", "400", new ResponseRegistDto());
        }
    }

    public ResultDto<Long> idDuplicationCheck(String userId) {
        try {
            Long check = authRepository.countByLadderAccountIdAndDelYn(userId, 1);

            return ResultDto.of("success", "200", check);
        }catch (Exception e){
            return ResultDto.of("fail", "400", -1L);
        }
    }

    public ResultDto<String> withdrawAccount(String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            LadderAccount ladderAccount = authRepository.findByLadderAccountId(userId)
                    .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다. ladderAccountId : " + userId));

            ladderAccount.remove();
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            CookieUtil.deleteCookie(request, response, tokenProvider.REFRESH_TOKEN);
            return ResultDto.of("success", "200", ladderAccount.getLadderAccountId());
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", "");
        }
    }

    public ResultDto<ResponseLoginDto> login(RequestLoginDto requestLoginDto, HttpServletResponse response) {
        try{
            LadderAccount ladderAccount = authRepository.findByLadderAccountIdAndDelYn(requestLoginDto.getLadderAccountId(), 1)
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + requestLoginDto.getLadderAccountId()));

            if(bCryptPasswordEncoder.matches(requestLoginDto.getLadderAccountPassword(), ladderAccount.getLadderAccountPassword())){
                String ladderAccountId = ladderAccount.getLadderAccountId();
                String ladderAccountAuth = ladderAccount.getLadderAccountAuth();
                String accessToken = tokenProvider.generateToken(ladderAccountId, ladderAccountAuth, tokenProvider.ACCESS_TOKEN_EXPIRED);
                String refreshToken = tokenProvider.generateToken(ladderAccountId, ladderAccountAuth, tokenProvider.REFRESH_TOKEN_EXPIRED);

                long refreshTokenId = ladderAccount.getRefreshToken() == null ? -1 : ladderAccount.getRefreshToken().getId();
                RefreshToken refreshTokenEntity = refreshTokenRepository.findById(refreshTokenId)
                        .map(entity -> entity.update(refreshToken))
                        .orElse(new RefreshToken(refreshToken));

                refreshTokenRepository.save(refreshTokenEntity);
                ladderAccount.setRefreshToken(refreshTokenEntity);
                CookieUtil.addCookie(response, tokenProvider.REFRESH_TOKEN, refreshToken, (int)tokenProvider.REFRESH_TOKEN_EXPIRED.toSeconds(), true);
                return ResultDto.of("success", "200", new ResponseLoginDto(ladderAccount, accessToken));
            }else {
                return ResultDto.of("fail", "400", new ResponseLoginDto());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseLoginDto());
        }

    }

    public ResultDto<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            CookieUtil.deleteCookie(request, response, tokenProvider.REFRESH_TOKEN);
            return ResultDto.of("success", "200", "logout");
        }catch (Exception e){
            return ResultDto.of("fail", "400", "logout");
        }
    }

    public ResultDto<List<ResponseLadderAccountDto>> searchUsers() {
        try {
            List<ResponseLadderAccountDto> responseLadderAccountDtos = authRepository.findByDelYn(1).stream()
                    .map(ResponseLadderAccountDto::new).collect(Collectors.toList());

            return ResultDto.of("success", "200", responseLadderAccountDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ArrayList<ResponseLadderAccountDto>());
        }
    }
}
