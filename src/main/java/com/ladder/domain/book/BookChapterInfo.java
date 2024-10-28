package com.ladder.domain.book;

import com.ladder.domain.common.CommonColumns1;
import com.ladder.dto.book.RequestBookChapterContent;
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

    public BookChapterInfo(RequestBookChapterContent requestBookChapterContent, BookInfo bookInfo){
        this.bookChapterInfoTitle = requestBookChapterContent.getBookChapterInfoTitle();
        this.bookChapterInfoContent = requestBookChapterContent.getBookChapterInfoContent();
        this.bookInfo = bookInfo;
    }

    public void updateAll(RequestBookChapterContent requestBookChapterContent) {
        this.bookChapterInfoTitle = requestBookChapterContent.getBookChapterInfoTitle();
        this.bookChapterInfoContent = requestBookChapterContent.getBookChapterInfoContent();
    }
}
