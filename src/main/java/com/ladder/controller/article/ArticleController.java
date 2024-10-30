package com.ladder.controller.article;

import com.ladder.dto.article.ResponseArticleCategpryDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/article/category/list")
    public ResponseEntity<ResultDto<List<ResponseArticleCategpryDto>>> searchArticleCategoryList(){
        return ResponseEntity.ok().body(articleService.searchArticleCategoryList());
    }

}
