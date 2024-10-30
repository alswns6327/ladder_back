package com.ladder.domain.book;

import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.book.RequestBookChapterContentDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book_chapter_info")
public class BookChapterInfo extends CommonColumns1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_CHAPTER_INFO_SEQ", nullable = false, updatable = false)
    private Long id;

    @Column(name = "BOOK_CHAPTER_INFO_TITLE", nullable = false)
    private String bookChapterInfoTitle;

    @Column(name = "BOOK_CHAPTER_INFO_CONTENT", nullable = false)
    private String bookChapterInfoContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_INFO_SEQ")
    private BookInfo bookInfo;

    public BookChapterInfo(RequestBookChapterContentDto requestBookChapterContentDto, BookInfo bookInfo){
        this.bookChapterInfoTitle = requestBookChapterContentDto.getBookChapterInfoTitle();
        this.bookChapterInfoContent = requestBookChapterContentDto.getBookChapterInfoContent();
        this.bookInfo = bookInfo;
    }

    public void updateAll(RequestBookChapterContentDto requestBookChapterContentDto) {
        this.bookChapterInfoTitle = requestBookChapterContentDto.getBookChapterInfoTitle();
        this.bookChapterInfoContent = requestBookChapterContentDto.getBookChapterInfoContent();
    }
}
