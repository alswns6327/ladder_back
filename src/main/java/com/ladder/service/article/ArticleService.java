package com.ladder.service.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ladder.domain.article.Article;
import com.ladder.domain.article.ArticleCategory;
import com.ladder.domain.article.ArticleSubCategory;
import com.ladder.dto.article.*;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.article.ArticleCategoryRepository;
import com.ladder.repository.article.ArticleRepository;
import com.ladder.repository.article.ArticleSubCategoryRepository;
import com.ladder.util.DecodeSearchParam;
import com.ladder.vo.article.ArticleSearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleCategoryRepository articleCategoryRepository;
    private final ArticleSubCategoryRepository articleSubCategoryRepository;

    public ResultDto<List<ResponseArticleCategpryDto>> searchArticleCategoryList(String userId) {
        try {
            List<ResponseArticleCategpryDto> responseArticleCategpryDtos =
            articleCategoryRepository.findByFirstSaveUserAndDelYn(userId, 0).stream()
                    .map((articleCategory -> { List<ResponseArticleSubCategoryDto> responseArticleSubCategoryDtos =
                            articleCategory.getArticleSubCategories().stream()
                                    .filter(subCategory -> subCategory.getDelYn() != 1) // JPQL로 수정 예정
                                    .map(ResponseArticleSubCategoryDto::new)
                                    .collect(Collectors.toList());
                        return new ResponseArticleCategpryDto(articleCategory, responseArticleSubCategoryDtos);
                    }))
                    .collect(Collectors.toList());

            return ResultDto.of("success", "200", responseArticleCategpryDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ArrayList<ResponseArticleCategpryDto>());
        }
    }

    public ResultDto<ResponseArticleCategpryDto> saveArticleCategory(RequestArticleCategoryDto requestArticleCategoryDto) {
        try {
            ArticleCategory articleCategory = new ArticleCategory(requestArticleCategoryDto);
            articleCategory = articleCategoryRepository.save(articleCategory);

            return ResultDto.of("success", "200", new ResponseArticleCategpryDto(articleCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleCategpryDto());
        }
    }

    public ResultDto<ResponseArticleCategpryDto> updateArticleCategory(RequestArticleCategoryDto requestArticleCategoryDto) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(requestArticleCategoryDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + requestArticleCategoryDto.getCategorySeq()));

            articleCategory.updateAll(requestArticleCategoryDto);
            return ResultDto.of("success", "200", new ResponseArticleCategpryDto(articleCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleCategpryDto());
        }
    }


    public ResultDto<ResponseArticleCategpryDto> deleteArticleCategory(Long categorySeq) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(categorySeq)
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + categorySeq));

            articleCategory.remove();
            return ResultDto.of("success", "200", new ResponseArticleCategpryDto(articleCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleCategpryDto());
        }
    }

    public ResultDto<ResponseArticleSubCategoryDto> saveArticleSubCategory(RequestArticleSubCategoryDto requestArticleSubCategoryDto) {
        try {
            ArticleCategory articleCategory = articleCategoryRepository.findById(requestArticleSubCategoryDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + requestArticleSubCategoryDto.getCategorySeq()));
            ArticleSubCategory articleSubCategory = new ArticleSubCategory(requestArticleSubCategoryDto, articleCategory);
            articleSubCategory = articleSubCategoryRepository.save(articleSubCategory);
            return ResultDto.of("success", "200", new ResponseArticleSubCategoryDto(articleSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleSubCategoryDto());
        }
    }

    public ResultDto<ResponseArticleSubCategoryDto> updateArticleSubCategory(RequestArticleSubCategoryDto requestArticleSubCategoryDto) {
        try {
            ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(requestArticleSubCategoryDto.getSubCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestArticleSubCategoryDto.getSubCategorySeq()));

            articleSubCategory.updateAll(requestArticleSubCategoryDto);
            return ResultDto.of("success", "200", new ResponseArticleSubCategoryDto(articleSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleSubCategoryDto());
        }
    }

    public ResultDto<ResponseArticleSubCategoryDto> deleteArticleSubCategory(Long subCategorySeq) {
        try {
            ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(subCategorySeq)
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + subCategorySeq));

            articleSubCategory.remove();
            return ResultDto.of("success", "200", new ResponseArticleSubCategoryDto(articleSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleSubCategoryDto());
        }
    }

    public ResultDto<ResponseArticleDto> saveArticle(RequestArticleDto requestArticleDto) {
        try {
            ArticleCategory articleCategory =
                    requestArticleDto.getCategorySeq() == null ? null : articleCategoryRepository.findById(requestArticleDto.getCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + requestArticleDto.getCategorySeq()));
            ArticleSubCategory articleSubCategory =
                    requestArticleDto.getSubCategorySeq() == null ? null : articleSubCategoryRepository.findById(requestArticleDto.getSubCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestArticleDto.getSubCategorySeq()));

            Article article = new Article(requestArticleDto, articleCategory, articleSubCategory);

            articleRepository.save(article);

            return ResultDto.of("success", "200", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleDto());
        }
    }

    public ResultDto<ResponseArticleDto> updateArticle(RequestArticleDto requestArticleDto) {
        try {
            Article article = articleRepository.findById(requestArticleDto.getArticleSeq())
                    .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다.  articleSeq" + requestArticleDto.getArticleSeq()));
            ArticleCategory articleCategory =
                    requestArticleDto.getCategorySeq() == null ? null : articleCategoryRepository.findById(requestArticleDto.getCategorySeq())
                            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + requestArticleDto.getCategorySeq()));
            ArticleSubCategory articleSubCategory =
                    requestArticleDto.getSubCategorySeq() == null ? null : articleSubCategoryRepository.findById(requestArticleDto.getSubCategorySeq())
                            .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestArticleDto.getSubCategorySeq()));

            article.updateAll(requestArticleDto, articleCategory, articleSubCategory);
            return ResultDto.of("success", "200", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleDto());
        }
    }

    public ResultDto<List<ResponseArticleDto>> searchArticleList(String searchParam) {
        try {
            ArticleSearchParam articleSearchParam = DecodeSearchParam.decodeSearchParam(searchParam, ArticleSearchParam.class);

            List<Article> articles;
            if(articleSearchParam.getCategorySeq() != null) {
                ArticleCategory articleCategory = articleCategoryRepository.findById(articleSearchParam.getCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + articleSearchParam.getCategorySeq()));
                articles = articleRepository.findByFirstSaveUserAndArticleCategoryAndDelYn(articleSearchParam.getLadderAccountId(), articleCategory, 0);
            }else if(articleSearchParam.getSubCategorySeq() != null) {
                ArticleSubCategory articleSubCategory = articleSubCategoryRepository.findById(articleSearchParam.getSubCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + articleSearchParam.getSubCategorySeq()));
                articles = articleRepository.findByFirstSaveUserAndArticleSubCategoryAndDelYn(articleSearchParam.getLadderAccountId(), articleSubCategory, 0);
            }else {
                articles = articleRepository.findByFirstSaveUserAndDelYn(articleSearchParam.getLadderAccountId(), 0);
            }


            List<ResponseArticleDto> responseArticleDtos = articles.stream()
                    .map(ResponseArticleDto::new).collect(Collectors.toList());

            return ResultDto.of("success", "200", responseArticleDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ArrayList<ResponseArticleDto>());
        }
    }

    public ResultDto<ResponseArticleDto> searchArticle(Long articleSeq) {
        try {
            Article article = articleRepository.findById(articleSeq)
                    .orElseThrow(() -> new IllegalArgumentException("글 목록을 찾을 수 없습니다 : articleSeq : " + articleSeq));

            return ResultDto.of("success", "200", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleDto());
        }
    }

    public ResultDto<ResponseArticleDto> deleteArticle(Long articleSeq) {
        try {
            Article article = articleRepository.findById(articleSeq)
                    .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다. articleSeq : " + articleSeq));

            article.remove();
            return ResultDto.of("success", "200", new ResponseArticleDto(article));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseArticleDto());
        }
    }
}
