package com.ladder.dto.edu;

import com.ladder.domain.article.ArticleSubCategory;
import com.ladder.domain.edu.EduSubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEduSubCategoryDto {
    private Long subCategorySeq;
    private Long categorySeq;
    private String subCategoryName;

    public ResponseEduSubCategoryDto(EduSubCategory eduSubCategory) {
        this.subCategorySeq = eduSubCategory.getId();
        this.categorySeq = eduSubCategory.getEduCategory().getId();
        this.subCategoryName = eduSubCategory.getSubCategoryName();
    }
}
