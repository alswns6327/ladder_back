package com.ladder.repository.article;

import com.ladder.domain.article.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {

    List<ArticleCategory> findByFirstSaveUserAndDelYn(String userId, int delYn);

}
