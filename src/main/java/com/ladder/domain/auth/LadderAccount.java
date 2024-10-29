package com.ladder.domain.auth;

import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.auth.RequestRegistDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ladder_account")
public class LadderAccount extends CommonColumns1 implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LADDER_ACCOUNT_SEQ", updatable = false, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REFRESH_TOKEN_ID")
    private RefreshToken refreshToken;

    @Column(name = "LADDER_ACCOUNT_ID", nullable = false)
    private String ladderAccountId;

    @Column(name = "LADDER_ACCOUNT_PASSWORD", nullable = false)
    private String ladderAccountPassword;

    @Column(name = "LADDER_ACCOUNT_NAME", nullable = false)
    private String ladderAccountName;

    @Column(name = "LADDER_ACCOUNT_EMAIL", nullable = false)
    private String ladderAccountEmail;

    @Column(name = "LADDER_ACCOUNT_AUTH", nullable = false)
    private String ladderAccountAuth;

    public LadderAccount(RequestRegistDto requestRegistDto) {
        this.ladderAccountId = requestRegistDto.getLadderAccountId();
        this.ladderAccountPassword = requestRegistDto.getLadderAccountPassword();
        this.ladderAccountName = requestRegistDto.getLadderAccountName();
        this.ladderAccountEmail = requestRegistDto.getLadderAccountEmail();
        this.ladderAccountAuth = "USER";
    }

    public LadderAccount(String ladderAccountId, String ladderAccountAuth){
        this.ladderAccountId = ladderAccountId;
        this.ladderAccountAuth = ladderAccountAuth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.ladderAccountAuth);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
