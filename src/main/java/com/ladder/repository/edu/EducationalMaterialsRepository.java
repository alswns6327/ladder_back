package com.ladder.repository.edu;

import com.ladder.domain.article.Article;
import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.article.ArticleSubCategory;
import com.ladder.domain.edu.EduCategory;
import com.ladder.domain.edu.EduSubCategory;
import com.ladder.domain.edu.EducationalMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationalMaterialsRepository extends JpaRepository<EducationalMaterials, Long> {
    List<EducationalMaterials> findByFirstSaveUserAndDelYn(String ladderAccountId, int delYn);
    List<EducationalMaterials> findByFirstSaveUserAndEduCategoryAndDelYn(String ladderAccountId, EduCategory eduCategory, int delYn);
    List<EducationalMaterials> findByFirstSaveUserAndEduSubCategoryAndDelYn(String ladderAccountId, EduSubCategory eduSubCategory, int delYn);
}
