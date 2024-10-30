package com.ladder.domain.article;

import com.ladder.domain.common.CommonColumns1;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "article_sub_category")
public class ArticleSubCategory extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_SUB_CATEGORY_SEQ", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_CATEGORY_SEQ")
    private ArticleCategory articleCategory;

    @Column(name = "SUB_CATEGORY_NAME", nullable = false)
    private String subCategoryName;
}
