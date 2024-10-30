package com.ladder.dto.article;

import com.ladder.domain.article.ArticleSubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArticleSubCategoryDto {
    private Long subCategorySeq;
    private Long categorySeq;
    private String subCategoryName;

    public ResponseArticleSubCategoryDto(ArticleSubCategory articleSubCategory) {
        this.subCategorySeq = articleSubCategory.getId();
        this.categorySeq = articleSubCategory.getArticleCategory().getId();
        this.subCategoryName = articleSubCategory.getSubCategoryName();
    }
}
