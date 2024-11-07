package com.ladder.dto.edu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEduDto {

    private Long eduSeq;
    private Long categorySeq;
    private Long subCategorySeq;
    private String title;
    private String article;
}
