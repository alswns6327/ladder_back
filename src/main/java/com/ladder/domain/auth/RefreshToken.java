package com.ladder.domain.auth;

import com.ladder.domain.common.CommonColumns1;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFRESH_TOKEN_ID", updatable = false, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "refreshToken", fetch = FetchType.LAZY)
    private LadderAccount ladderAccount;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;

    public RefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
