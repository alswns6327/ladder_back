package com.ladder.dto.book;

import com.ladder.domain.book.BookChapterInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookChapterContentDto {
    private Long bookInfoId;
    private Long bookChapterInfoId;
    private String bookChapterInfoTitle;
    private String bookChapterInfoContent;
    private String firstSaveUser;

    public ResponseBookChapterContentDto(BookChapterInfo bookChapterInfo) {
        this.bookInfoId = bookChapterInfo.getBookInfo().getId();
        this.bookChapterInfoId = bookChapterInfo.getId();
        this.bookChapterInfoTitle = bookChapterInfo.getBookChapterInfoTitle();
        this.bookChapterInfoContent = bookChapterInfo.getBookChapterInfoContent();
        this.firstSaveUser = bookChapterInfo.getFirstSaveUser();
    }
}
