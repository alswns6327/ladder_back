package com.ladder.dto.book;

import com.ladder.domain.book.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseBookInfoDto {

    private String bookName;
    private String bookAuthorName;
    private String bookTranslatorName;
    private MultipartFile bookImgFile;
    private String bookImgUrl;

    public ResponseBookInfoDto(BookInfo bookInfo){
        this.bookName = bookInfo.getBookName();
        this.bookAuthorName = bookInfo.getBookAuthorName();
        this.bookTranslatorName = bookInfo.getBookTransLatorName();
        this.bookImgUrl = bookInfo.getBookImgUrl();
    }
}
