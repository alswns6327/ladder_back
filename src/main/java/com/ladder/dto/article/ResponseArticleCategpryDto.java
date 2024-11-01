package com.ladder.dto.article;

import com.ladder.domain.article.ArticleCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArticleCategpryDto {

    private Long categorySeq;
    private String categoryName;
    private List<ResponseArticleSubCategoryDto> subCategories;

    public ResponseArticleCategpryDto(ArticleCategory articleCategory, List<ResponseArticleSubCategoryDto> responseArticleSubCategoryDtos) {
        this.categorySeq = articleCategory.getId();
        this.categoryName = articleCategory.getCategoryName();
        this.subCategories = responseArticleSubCategoryDtos;
    }

    public ResponseArticleCategpryDto(ArticleCategory articleCategory) {
        this.categorySeq = articleCategory.getId();
        this.categoryName = articleCategory.getCategoryName();
    }
}
