package com.ladder.repository.article;

import com.ladder.domain.article.ArticleSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSubCategoryRepository extends JpaRepository<ArticleSubCategory, Long> {
}
