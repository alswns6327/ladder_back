package com.ladder.repository.article;

import com.ladder.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByFirstSaveUserAndDelYn(String userId, int delYn);
}
