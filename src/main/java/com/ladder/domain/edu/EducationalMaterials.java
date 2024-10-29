package com.ladder.domain.edu;

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
}
