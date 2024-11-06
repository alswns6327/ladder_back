package com.ladder.dto.auth;

import com.ladder.domain.auth.LadderAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLadderAccountDto {

    private Long ladderAccountSeq;
    private String ladderAccountId;

    public ResponseLadderAccountDto(LadderAccount ladderAccount) {
        this.ladderAccountSeq = ladderAccount.getId();
        this.ladderAccountId = ladderAccount.getLadderAccountId();
    }
}
