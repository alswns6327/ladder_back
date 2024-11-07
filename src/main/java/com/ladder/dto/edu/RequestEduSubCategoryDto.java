package com.ladder.dto.edu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEduSubCategoryDto {
    private Long subCategorySeq;
    private Long categorySeq;
    private String subCategoryName;

}
