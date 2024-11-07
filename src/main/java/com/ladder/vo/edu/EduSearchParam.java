package com.ladder.vo.edu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EduSearchParam {
    private String ladderAccountId;
    private Long categorySeq;
    private Long subCategorySeq;
}
