package com.ladder.controller.article;

import com.ladder.dto.article.*;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/article/category/list")
    public ResponseEntity<ResultDto<List<ResponseArticleCategpryDto>>> searchArticleCategoryList(@RequestParam String userId){
        return ResponseEntity.ok().body(articleService.searchArticleCategoryList(userId));
    }

    @PostMapping("/article/category")
    public ResponseEntity<ResultDto<ResponseArticleCategpryDto>> saveArticleCategory(@RequestBody RequestArticleCategoryDto requestArticleCategoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.saveArticleCategory(requestArticleCategoryDto));
    }

    @PutMapping("/article/category")
    public ResponseEntity<ResultDto<ResponseArticleCategpryDto>> updateArticleCategory(@RequestBody RequestArticleCategoryDto requestArticleCategoryDto){
        return ResponseEntity.ok().body(articleService.updateArticleCategory(requestArticleCategoryDto));
    }

    @PostMapping("/article/sub-category")
    public ResponseEntity<ResultDto<ResponseArticleSubCategoryDto>> saveArticleCategory(@RequestBody RequestArticleSubCategoryDto requestArticleSubCategoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.saveArticleSubCategory(requestArticleSubCategoryDto));
    }

    @PutMapping("/article/sub-category")
    public ResponseEntity<ResultDto<ResponseArticleSubCategoryDto>> updateArticleCategory(@RequestBody RequestArticleSubCategoryDto requestArticleSubCategoryDto){
        return ResponseEntity.ok().body(articleService.updateArticleSubCategory(requestArticleSubCategoryDto));
    }

    @PostMapping("/article")
    public ResponseEntity<ResultDto<ResponseArticleDto>> saveArticle(@RequestBody RequestArticleDto requestArticleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.saveArticle(requestArticleDto));
    }

    @GetMapping("/article")
    public ResponseEntity<ResultDto<List<ResponseArticleDto>>> searchArticleList(@RequestParam String userId){
        return ResponseEntity.ok().body(articleService.searchArticleList(userId));
    }

    @GetMapping("/article/{articleSeq}")
    public ResponseEntity<ResultDto<ResponseArticleDto>> searchArticle(@PathVariable Long articleSeq){
        return ResponseEntity.ok().body(articleService.searchArticle(articleSeq));
    }
}
