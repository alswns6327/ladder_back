package com.ladder.service.article;

import com.ladder.domain.article.Article;
import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.article.ArticleSubCategory;
import com.ladder.dto.article.*;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.article.ArticleCategoryRepository;
import com.ladder.repository.article.ArticleRepository;
import com.ladder.repository.article.ArticleSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleCategoryRepository articleCategoryRepository;
    private final ArticleSubCategoryRepository articleSubCategoryRepository;

    public ResultDto<List<ResponseArticleCategpryDto>> searchArticleCategoryList(String userId) {
        try {
            List<ResponseArticleCategpryDto> responseArticleCategpryDtos =
            articleCategoryRepository.findByFirstSaveUserAndDelYn(userId, 1).stream()
                    .map((articleCategory -> { List<ResponseArticleSubCategoryDto> responseArticleSubCategoryDtos =
                            articleCategory.getArticleSubCategories().stream()
                                    .filter(subCategory -> subCategory.getDelYn() != 0) // JPQL로 수정 예정
                                    .map(ResponseArticleSubCategoryDto::new)
                                    .collect(Collectors.toList());
                        return new ResponseArticleCategpryDto(articleCategory, responseArticleSubCategoryDtos);
                    }))
                    .collect(Collectors.toList());

            return ResultDto.of("success", responseArticleCategpryDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ArrayList<ResponseArticleCategpryDto>());
        }
    }

    public ResultDto<ResponseArticleCategpryDto> saveArticleCategory(RequestArticleCategoryDto requestArticleCategoryDto) {
        try {
            ArticleCategory articleCategory = new ArticleCategory(requestArticleCategoryDto);
            articleCategory = articleCategoryRepository.save(articleCategory);

            return ResultDto.of("success", new ResponseArticleCategpryDto(articleCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleCategpryDto());
        }
    }

    public ResultDto<ResponseArticleCategpryDto> updateArticleCategory(RequestArticleCategoryDto requestArticleCategoryDto) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(requestArticleCategoryDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + requestArticleCategoryDto.getCategorySeq()));

            articleCategory.updateAll(requestArticleCategoryDto);
            articleCategoryRepository.save(articleCategory);
            return ResultDto.of("success", new ResponseArticleCategpryDto(articleCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleCategpryDto());
        }
    }


    public ResultDto<ResponseArticleCategpryDto> deleteArticleCategory(Long categorySeq) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(categorySeq)
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + categorySeq));

            articleCategory.remove();
            articleCategoryRepository.save(articleCategory);
            return ResultDto.of("success", new ResponseArticleCategpryDto(articleCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleCategpryDto());
        }
    }

    public ResultDto<ResponseArticleSubCategoryDto> saveArticleSubCategory(RequestArticleSubCategoryDto requestArticleSubCategoryDto) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(requestArticleSubCategoryDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + requestArticleSubCategoryDto.getCategorySeq()));
            ArticleSubCategory articleSubCategory = new ArticleSubCategory(requestArticleSubCategoryDto, articleCategory);
            articleSubCategory = articleSubCategoryRepository.save(articleSubCategory);
            return ResultDto.of("success", new ResponseArticleSubCategoryDto(articleSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleSubCategoryDto());
        }
    }

    public ResultDto<ResponseArticleSubCategoryDto> updateArticleSubCategory(RequestArticleSubCategoryDto requestArticleSubCategoryDto) {
        try {
            ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(requestArticleSubCategoryDto.getSubCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestArticleSubCategoryDto.getSubCategorySeq()));

            articleSubCategory.updateAll(requestArticleSubCategoryDto);
            articleSubCategoryRepository.save(articleSubCategory);
            return ResultDto.of("success", new ResponseArticleSubCategoryDto(articleSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleSubCategoryDto());
        }
    }

    public ResultDto<ResponseArticleSubCategoryDto> deleteArticleSubCategory(Long subCategorySeq) {
        try {
            ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(subCategorySeq)
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + subCategorySeq));

            articleSubCategory.remove();
            articleSubCategoryRepository.save(articleSubCategory);
            return ResultDto.of("success", new ResponseArticleSubCategoryDto(articleSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleSubCategoryDto());
        }
    }

    public ResultDto<ResponseArticleDto> saveArticle(RequestArticleDto requestArticleDto) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(requestArticleDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + requestArticleDto.getCategorySeq()));
            ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(requestArticleDto.getSubCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestArticleDto.getSubCategorySeq()));

            Article article = new Article(requestArticleDto, articleCategory, articleSubCategory);

            articleRepository.save(article);

            return ResultDto.of("success", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleDto());
        }
    }

    public ResultDto<ResponseArticleDto> updateArticle(RequestArticleDto requestArticleDto) {
        try {
            Article article = articleRepository.findById(requestArticleDto.getArticleSeq())
                    .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다.  articleSeq" + requestArticleDto.getArticleSeq()));
            ArticleCategory articleCategory = articleCategoryRepository.findById(requestArticleDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + requestArticleDto.getCategorySeq()));
            ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(requestArticleDto.getSubCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestArticleDto.getSubCategorySeq()));

            article.updateAll(requestArticleDto, articleCategory, articleSubCategory);

            articleRepository.save(article);

            return ResultDto.of("success", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleDto());
        }
    }

    public ResultDto<List<ResponseArticleDto>> searchArticleList(String userId) {
        try {
            List<ResponseArticleDto> responseArticleDtos = articleRepository.findByFirstSaveUserAndDelYn(userId, 1).stream()
                    .map(ResponseArticleDto::new).collect(Collectors.toList());

            return ResultDto.of("success", responseArticleDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ArrayList<ResponseArticleDto>());
        }
    }

    public ResultDto<ResponseArticleDto> searchArticle(Long articleSeq) {
        try {
            Article article = articleRepository.findById(articleSeq)
                    .orElseThrow(() -> new IllegalArgumentException("글 목록을 찾을 수 없습니다 : articleSeq : " + articleSeq));

            return ResultDto.of("success", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseArticleDto());
        }
    }
}
