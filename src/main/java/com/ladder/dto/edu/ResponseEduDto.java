package com.ladder.dto.edu;

import com.ladder.domain.article.Article;
import com.ladder.domain.edu.EducationalMaterials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEduDto {

    private Long eduSeq;
    private Long categorySeq;
    private Long subCategorySeq;
    private String title;
    private String content;
    private String categoryName;
    private String subCategoryName;
    public ResponseEduDto(EducationalMaterials educationalMaterials){
        this.title = educationalMaterials.getTitle();
        this.content = educationalMaterials.getContent();
        this.eduSeq = educationalMaterials.getId();
        this.categorySeq = educationalMaterials.getEduCategory().getId();
        this.subCategorySeq = educationalMaterials.getEduSubCategory().getId();
        this.categoryName = educationalMaterials.getEduCategory().getCategoryName();
        this.subCategoryName = educationalMaterials.getEduSubCategory().getSubCategoryName();
    }
}
