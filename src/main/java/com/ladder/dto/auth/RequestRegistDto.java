package com.ladder.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestRegistDto {

    private String ladderAccountId;
    private String ladderAccountPassword;
    private String ladderAccountName;
    private String ladderAccountEmail;

    public RequestRegistDto encryptPassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.ladderAccountPassword = bCryptPasswordEncoder.encode(this.ladderAccountPassword);
        return this;
    }

}
