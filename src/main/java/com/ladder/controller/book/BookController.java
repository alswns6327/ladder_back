package com.ladder.controller.book;

import com.ladder.dto.book.RequestBookChapterContent;
import com.ladder.dto.book.RequestBookInfoDto;
import com.ladder.dto.book.ResponseBookChapterContentDto;
import com.ladder.dto.book.ResponseBookInfoDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book/info")
    public ResponseEntity<ResultDto<ResponseBookInfoDto>> bookInfoSave (@ModelAttribute RequestBookInfoDto bookInfoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.bookInfoSave(bookInfoDto));
    }

    @GetMapping("/book/info/list")
    public ResponseEntity<ResultDto<List<ResponseBookInfoDto>>> bookInfoListSearch () {
        return ResponseEntity.ok().body(bookService.bookInfoListSearch());
    }

    @PostMapping("/book/chapter/content")
    public ResponseEntity<ResultDto<ResponseBookChapterContentDto>> bookChapterContentSave(@RequestBody RequestBookChapterContent requestBookChapterContent){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.bookChapterContentSave(requestBookChapterContent));
    }
}
