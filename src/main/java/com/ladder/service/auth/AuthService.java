package com.ladder.service.auth;

import com.ladder.config.jwt.TokenProvider;
import com.ladder.domain.auth.LadderAccount;
import com.ladder.domain.auth.RefreshToken;
import com.ladder.dto.auth.RequestLoginDto;
import com.ladder.dto.auth.RequestRegistDto;
import com.ladder.dto.auth.ResponseLoginDto;
import com.ladder.dto.auth.ResponseRegistDto;
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

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    public ResultDto<ResponseRegistDto> registAccount(RequestRegistDto requestRegistDto) {
        try{
            LadderAccount ladderAccount = authRepository.save(new LadderAccount(requestRegistDto.encryptPassword(bCryptPasswordEncoder)));
            return ResultDto.of("success", new ResponseRegistDto(ladderAccount.getLadderAccountId()));
        }catch (Exception e){
            return ResultDto.of("fail", new ResponseRegistDto());
        }
    }

    public ResultDto<ResponseLoginDto> login(RequestLoginDto requestLoginDto, HttpServletResponse response) {
        try{
            LadderAccount ladderAccount = authRepository.findByLadderAccountId(requestLoginDto.getLadderAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("not found : " + requestLoginDto.getLadderAccountId()));

            if(bCryptPasswordEncoder.matches(requestLoginDto.getLadderAccountPassword(), ladderAccount.getLadderAccountPassword())){
                String ladderAccountId = ladderAccount.getLadderAccountId();
                String ladderAccountAuth = ladderAccount.getLadderAccountAuth();
                String accessToken = tokenProvider.generateToken(ladderAccountId, ladderAccountAuth, tokenProvider.ACCESS_TOKEN_EXPIRED);
                String refreshToken = tokenProvider.generateToken(ladderAccountId, ladderAccountAuth, tokenProvider.REFRESH_TOKEN_EXPIRED);

                long refreshTokenId = ladderAccount.getRefreshToken() == null ? -1 : ladderAccount.getRefreshToken().getId();
                RefreshToken newRefreshToken = refreshTokenRepository.findById(refreshTokenId)
                        .map(entity -> entity.update(refreshToken))
                        .orElse(new RefreshToken(refreshToken));

                refreshTokenRepository.save(newRefreshToken);
                ladderAccount.setRefreshToken(newRefreshToken);
                authRepository.save(ladderAccount);
                CookieUtil.addCookie(response, tokenProvider.REFRESH_TOKEN, refreshToken, (int)tokenProvider.REFRESH_TOKEN_EXPIRED.toSeconds(), true);

                return ResultDto.of("login success", new ResponseLoginDto(ladderAccount, accessToken));
            }else {
                return ResultDto.of("login fail", new ResponseLoginDto());
            }
        }catch (Exception e){
            return ResultDto.of("login fail", new ResponseLoginDto());
        }

    }

    public ResultDto<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            CookieUtil.deleteCookie(request, response, tokenProvider.ACCESS_TOKEN);
            return ResultDto.of("logout success", "logout");
        }catch (Exception e){
            return ResultDto.of("logout fail", "logout");
        }
    }
}
