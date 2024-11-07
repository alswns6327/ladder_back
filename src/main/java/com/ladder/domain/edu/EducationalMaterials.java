package com.ladder.domain.edu;

import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.article.ArticleSubCategory;
import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.article.RequestArticleDto;
import com.ladder.dto.edu.RequestEduDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "educational_materials")
public class EducationalMaterials extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDUCATIONAL_MATERIALS_SEQ", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDU_CATEGORY_SEQ")
    private EduCategory eduCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDU_SUB_CATEGORY_SEQ")
    private EduSubCategory eduSubCategory;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;
    public EducationalMaterials (RequestEduDto requestEduDto, EduCategory eduCategory, EduSubCategory eduSubCategory){
        this.title = requestEduDto.getTitle();
        this.content = requestEduDto.getContent();
        this.eduCategory = eduCategory;
        this.eduSubCategory = eduSubCategory;
    }

    public void updateAll(RequestEduDto requestEduDto, EduCategory eduCategory, EduSubCategory eduSubCategory) {
        this.title = requestEduDto.getTitle();
        this.content = requestEduDto.getContent();
        this.eduCategory = eduCategory;
        this.eduSubCategory = eduSubCategory;
    }

}
