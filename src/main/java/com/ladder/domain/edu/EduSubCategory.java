package com.ladder.domain.edu;

import com.ladder.domain.edu.EduCategory;
import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.edu.RequestEduSubCategoryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "edu_sub_category")
public class EduSubCategory extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDU_SUB_CATEGORY_SEQ", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDU_CATEGORY_SEQ")
    private EduCategory eduCategory;

    @Column(name = "SUB_CATEGORY_NAME", nullable = false)
    private String subCategoryName;

    public EduSubCategory(RequestEduSubCategoryDto requestEduSubCategoryDto, EduCategory EduCategory){
        this.subCategoryName = requestEduSubCategoryDto.getSubCategoryName();
        this.eduCategory = EduCategory;
    }

    public void updateAll(RequestEduSubCategoryDto requestEduSubCategoryDto) {
        this.subCategoryName = requestEduSubCategoryDto.getSubCategoryName();
    }
}
