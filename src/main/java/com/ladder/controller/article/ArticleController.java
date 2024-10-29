package com.ladder.controller.article;

import com.ladder.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

}
