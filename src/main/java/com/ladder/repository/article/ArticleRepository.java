package com.ladder.repository.article;

import com.ladder.domain.article.Article;
import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.article.ArticleSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByFirstSaveUserAndDelYn(String ladderAccountId, int delYn);
    List<Article> findByFirstSaveUserAndArticleCategoryAndDelYn(String ladderAccountId, ArticleCategory articleCategory, int delYn);
    List<Article> findByFirstSaveUserAndArticleSubCategoryAndDelYn(String ladderAccountId, ArticleSubCategory articleSubCategory, int delYn);
}
