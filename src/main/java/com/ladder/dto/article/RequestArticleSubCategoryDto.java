package com.ladder.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestArticleSubCategoryDto {
    private Long subCategorySeq;
    private Long categorySeq;
    private String subCategoryName;

}
