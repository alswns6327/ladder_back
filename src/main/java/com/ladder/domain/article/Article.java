package com.ladder.domain.article;

import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.article.RequestArticleDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "article")
public class Article extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_SEQ", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_CATEGORY_SEQ")
    private ArticleCategory articleCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_SUB_CATEGORY_SEQ")
    private ArticleSubCategory articleSubCategory;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    public Article (RequestArticleDto requestArticleDto, ArticleCategory articleCategory, ArticleSubCategory articleSubCategory){
        this.title = requestArticleDto.getTitle();
        this.content = requestArticleDto.getContent();
        this.articleCategory = articleCategory;
        this.articleSubCategory = articleSubCategory;
    }

    public void updateAll(RequestArticleDto requestArticleDto, ArticleCategory articleCategory, ArticleSubCategory articleSubCategory) {
        this.title = requestArticleDto.getTitle();
        this.content = requestArticleDto.getContent();
        this.articleCategory = articleCategory;
        this.articleSubCategory = articleSubCategory;
    }
}
