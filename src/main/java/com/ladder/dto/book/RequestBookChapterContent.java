package com.ladder.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBookChapterContent {
    private Long bookInfoId;
    private String bookChapterInfoTitle;
    private String bookChapterInfoContent;
}
