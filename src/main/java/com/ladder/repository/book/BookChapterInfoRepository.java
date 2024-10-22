package com.ladder.repository.book;

import com.ladder.domain.book.BookChapterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookChapterInfoRepository extends JpaRepository<BookChapterInfo, Long> {
}
