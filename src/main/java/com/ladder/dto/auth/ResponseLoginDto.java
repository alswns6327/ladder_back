package com.ladder.dto.auth;

import com.ladder.domain.auth.LadderAccount;
import com.ladder.dto.common.ResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseLoginDto {
    private String ladderAccountId;
    private String ladderAccountPassword;
    private String ladderAccountName;
    private String ladderAccountEmail;
    private String ladderAccountAuth;
    private String accessToken;

    public ResponseLoginDto(LadderAccount ladderAccount, String accessToken) {
        this.ladderAccountId = ladderAccount.getLadderAccountId();
        this.ladderAccountPassword = ladderAccount.getLadderAccountPassword();
        this.ladderAccountName = ladderAccount.getLadderAccountName();
        this.ladderAccountEmail = ladderAccount.getLadderAccountEmail();
        this.ladderAccountAuth = ladderAccount.getLadderAccountAuth();
        this.accessToken = accessToken;
    }
}
