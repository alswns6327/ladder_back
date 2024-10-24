package com.ladder.domain.book;

import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.book.RequestBookInfoDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book_info")
public class BookInfo extends CommonColumns1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_info_seq", updatable = false, nullable = false)
    private Long id;

    @Column(name = "BOOK_NAME", nullable = false)
    private String bookName;

    @Column(name = "BOOK_AUTHOR_NAME")
    private String bookAuthorName;

    @Column(name = "BOOK_TRANSLATOR_NAME")
    private String bookTransLatorName;

    @Column(name = "BOOK_IMG_URL")
    private String bookImgUrl;

    @Column(name = "BOOK_IMG_FILE_EXTENSION")
    private String bookImgFileExtension;

    @OneToMany(mappedBy = "bookInfo", fetch = FetchType.LAZY)
    private List<BookChapterInfo> bookChapterInfo;

    public BookInfo(RequestBookInfoDto bookInfoDto){
        this.bookName = bookInfoDto.getBookName();
        this.bookAuthorName = bookInfoDto.getBookAuthorName();
        this.bookTransLatorName = bookInfoDto.getBookTranslatorName();
    }

    public void updateAll(RequestBookInfoDto bookInfoDto) {
        this.bookName = bookInfoDto.getBookName();
        this.bookAuthorName = bookInfoDto.getBookAuthorName();
        this.bookTransLatorName = bookInfoDto.getBookTranslatorName();
    }
}
