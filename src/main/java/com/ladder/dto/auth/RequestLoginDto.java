package com.ladder.dto.auth;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestLoginDto {
    private String ladderAccountId;
    private String ladderAccountPassword;
}
