package com.ladder.repository.book;

import com.ladder.domain.book.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {

    List<BookInfo> findByDelYn (int delYn);
}
