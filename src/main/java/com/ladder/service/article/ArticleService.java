package com.ladder.service.article;

import com.ladder.repository.article.ArticleCategoryRepository;
import com.ladder.repository.article.ArticleRepository;
import com.ladder.repository.article.ArticleSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleCategoryRepository articleCategoryRepository;
    private final ArticleSubCategoryRepository articleSubCategoryRepository;
}
