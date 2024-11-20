package com.ladder.repository.book;

import com.ladder.domain.book.BookChapterInfo;
import com.ladder.domain.book.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookChapterInfoRepository extends JpaRepository<BookChapterInfo, Long> {
    List<BookChapterInfo> findByBookInfoAndDelYn(BookInfo bookInfo, int delYn);
}
