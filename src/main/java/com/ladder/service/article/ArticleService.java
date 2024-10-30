package com.ladder.service.article;

import com.ladder.dto.article.ResponseArticleCategpryDto;
import com.ladder.dto.article.ResponseArticleSubCategoryDto;
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

    public ResultDto<List<ResponseArticleCategpryDto>> searchArticleCategoryList() {
        try {
            List<ResponseArticleCategpryDto> responseArticleCategpryDtos =
            articleCategoryRepository.findByDelYn(1).stream()
                    .map((articleCategory -> { List<ResponseArticleSubCategoryDto> responseArticleSubCategoryDtos =
                            articleCategory.getArticleSubCategories().stream()
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
}
