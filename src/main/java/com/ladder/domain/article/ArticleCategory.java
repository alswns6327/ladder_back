package com.ladder.domain.article;

import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.article.RequestArticleCategoryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "article_category")
public class ArticleCategory extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_CATEGORY_SEQ", nullable = false, updatable = false)
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "articleCategory", fetch = FetchType.EAGER)
    private List<ArticleSubCategory> articleSubCategories;

    public ArticleCategory(RequestArticleCategoryDto requestArticleCategoryDto){
        this.categoryName = requestArticleCategoryDto.getCategoryName();
    }

    public void updateAll(RequestArticleCategoryDto requestArticleCategoryDto) {
        this.categoryName = requestArticleCategoryDto.getCategoryName();
    }
}
