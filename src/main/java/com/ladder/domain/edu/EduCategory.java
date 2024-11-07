package com.ladder.domain.edu;

import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.article.ArticleSubCategory;
import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.article.RequestArticleCategoryDto;
import com.ladder.dto.article.RequestArticleDto;
import com.ladder.dto.edu.RequestEduCategoryDto;
import com.ladder.dto.edu.RequestEduDto;
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
@Table(name = "edu_category")
public class EduCategory extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDU_CATEGORY_SEQ", nullable = false, updatable = false)
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "eduCategory")
    private List<EduSubCategory> eduSubCategories;

    public EduCategory(RequestEduCategoryDto requestEduCategoryDto){
        this.categoryName = requestEduCategoryDto.getCategoryName();
    }

    public void updateAll(RequestEduCategoryDto requestEduCategoryDto) {
        this.categoryName = requestEduCategoryDto.getCategoryName();
    }
}
