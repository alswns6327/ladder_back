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

    private Long id;
    private String bookName;
    private String bookAuthorName;
    private String bookTranslatorName;
    private String bookImgFile;
    private String bookImgUrl;
    private String bookImgFileExtension;

    public ResponseBookInfoDto(BookInfo bookInfo){
        this.id = bookInfo.getId();
        this.bookName = bookInfo.getBookName();
        this.bookAuthorName = bookInfo.getBookAuthorName();
        this.bookTranslatorName = bookInfo.getBookTransLatorName();
        this.bookImgUrl = bookInfo.getBookImgUrl();
        this.bookImgFileExtension = bookInfo.getBookImgFileExtension();
    }

    public ResponseBookInfoDto(BookInfo bookInfo, String bookImgFile){
        this.id = bookInfo.getId();
        this.bookName = bookInfo.getBookName();
        this.bookAuthorName = bookInfo.getBookAuthorName();
        this.bookTranslatorName = bookInfo.getBookTransLatorName();
        this.bookImgUrl = bookInfo.getBookImgUrl();
        this.bookImgFileExtension = bookInfo.getBookImgFileExtension();
        this.bookImgFile = bookImgFile;
    }
}
