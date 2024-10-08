package com.ladder.repository.auth;

import com.ladder.domain.auth.LadderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<LadderAccount, Long> {
    Optional<LadderAccount> findByLadderAccountId(String ladderAccountId);
}
