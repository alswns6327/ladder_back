package com.ladder.dto.common;

import com.ladder.domain.common.LadderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMenuDto {
    private Long menuSeq;
    private String menuPath;
    private String menuName;

    public ResponseMenuDto(LadderMenu ladderMenu){
        this.menuSeq = ladderMenu.getId();
        this.menuPath = ladderMenu.getLadderMenuPath();
        this.menuName = ladderMenu.getLadderMenuName();
    }
}
