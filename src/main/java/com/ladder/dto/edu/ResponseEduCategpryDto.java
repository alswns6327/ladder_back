package com.ladder.dto.edu;

import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.edu.EduCategory;
import com.ladder.dto.article.ResponseArticleSubCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEduCategpryDto {

    private Long categorySeq;
    private String categoryName;
    private List<ResponseEduSubCategoryDto> subCategories;

    public ResponseEduCategpryDto(EduCategory eduCategory, List<ResponseEduSubCategoryDto> responseEduSubCategoryDtos) {
        this.categorySeq = eduCategory.getId();
        this.categoryName = eduCategory.getCategoryName();
        this.subCategories = responseEduSubCategoryDtos;
    }

    public ResponseEduCategpryDto(EduCategory eduCategory) {
        this.categorySeq = eduCategory.getId();
        this.categoryName = eduCategory.getCategoryName();
    }
}
