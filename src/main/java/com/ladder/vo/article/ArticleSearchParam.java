package com.ladder.vo.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchParam {
    private String ladderAccountId;
    private Long categorySeq;
    private Long subCategorySeq;
}
