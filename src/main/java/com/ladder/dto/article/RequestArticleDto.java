package com.ladder.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestArticleDto {

    private Long categorySeq;
    private Long subCategorySeq;
    private String title;
    private String article;
}
