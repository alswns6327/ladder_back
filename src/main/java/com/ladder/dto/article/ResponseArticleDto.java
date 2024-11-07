package com.ladder.dto.article;

import com.ladder.domain.article.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArticleDto {

    private Long articleSeq;
    private Long categorySeq;
    private Long subCategorySeq;
    private String title;
    private String content;
    private String categoryName;
    private String subCategoryName;
    public ResponseArticleDto (Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
        this.articleSeq = article.getId();
        this.categorySeq = article.getArticleCategory().getId();
        this.subCategorySeq = article.getArticleSubCategory().getId();
        this.categoryName = article.getArticleCategory().getCategoryName();
        this.subCategoryName = article.getArticleSubCategory().getSubCategoryName();
    }
}
