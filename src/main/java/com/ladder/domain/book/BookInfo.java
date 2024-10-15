package com.ladder.domain.book;

import com.ladder.domain.common.CommonColumns1;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
