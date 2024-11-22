package com.ladder.repository.common;

import com.ladder.domain.common.LadderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LadderMenuRepository extends JpaRepository<LadderMenu, Long> {
    List<LadderMenu> findByDelYnOrderByLadderMenuOrderAsc (int delYn);
}
