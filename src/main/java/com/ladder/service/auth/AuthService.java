package com.ladder.service.auth;

import com.ladder.domain.auth.LadderAccount;
import com.ladder.dto.auth.RequestRegistDto;
import com.ladder.dto.auth.ResponseRegistDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.auth.AuthRepository;
import com.ladder.repository.auth.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResultDto<ResponseRegistDto> registAccount(RequestRegistDto requestRegistDto) {
        LadderAccount ladderAccount = authRepository.save(new LadderAccount(requestRegistDto.encryptPassword(bCryptPasswordEncoder)));
        return ResultDto.of("success", new ResponseRegistDto(ladderAccount.getLadderAccountId()));
    }
}
