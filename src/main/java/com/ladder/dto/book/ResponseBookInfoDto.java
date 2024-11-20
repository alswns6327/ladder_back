package com.ladder.dto.book;

import com.ladder.domain.book.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseBookInfoDto {

    private Long bookInfoId;
    private String bookName;
    private String bookDescription;
    private String bookAuthorName;
    private String bookTranslatorName;
    private String bookImgFile;
    private String bookImgUrl;
    private String bookImgFileExtension;
    private String firstSaveUser;

    public ResponseBookInfoDto(BookInfo bookInfo){
        this.bookInfoId = bookInfo.getId();
        this.bookName = bookInfo.getBookName();
        this.bookAuthorName = bookInfo.getBookAuthorName();
        this.bookTranslatorName = bookInfo.getBookTransLatorName();
        this.bookImgUrl = bookInfo.getBookImgUrl();
        this.bookImgFileExtension = bookInfo.getBookImgFileExtension();
        this.firstSaveUser = bookInfo.getFirstSaveUser();
    }

    public ResponseBookInfoDto(BookInfo bookInfo, String bookImgFile){
        this.bookInfoId = bookInfo.getId();
        this.bookDescription = bookInfo.getBookDescription();
        this.bookName = bookInfo.getBookName();
        this.bookAuthorName = bookInfo.getBookAuthorName();
        this.bookTranslatorName = bookInfo.getBookTransLatorName();
        this.bookImgUrl = bookInfo.getBookImgUrl();
        this.bookImgFileExtension = bookInfo.getBookImgFileExtension();
        this.firstSaveUser = bookInfo.getFirstSaveUser();
        this.bookImgFile = bookImgFile;
    }
}
