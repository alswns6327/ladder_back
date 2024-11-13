package com.ladder.controller.book;

import com.ladder.dto.book.RequestBookChapterContentDto;
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
    public ResponseEntity<ResultDto<List<ResponseBookInfoDto>>> bookInfoListSearch (@RequestParam String ladderAccountId) {
        return ResponseEntity.ok().body(bookService.bookInfoListSearch(ladderAccountId));
    }

    @GetMapping("/book/info/{bookInfoId}")
    public ResponseEntity<ResultDto<ResponseBookInfoDto>> searchBookInfo(@PathVariable Long bookInfoId){
        return ResponseEntity.ok().body(bookService.searchBookInfo(bookInfoId));
    }

    @PutMapping("/book/info")
    public ResponseEntity<ResultDto<ResponseBookInfoDto>> updateBookInfo(@ModelAttribute RequestBookInfoDto bookInfoDto){
        return ResponseEntity.ok().body(bookService.updateBookInfo(bookInfoDto));
    }

    @DeleteMapping("/book/info/{bookInfoId}")
    public ResponseEntity<ResultDto<ResponseBookInfoDto>> deleteBookInfo(@PathVariable Long bookInfoId){
        return ResponseEntity.ok().body(bookService.deleteBookInfo(bookInfoId));
    }

    @PostMapping("/book/chapter/content")
    public ResponseEntity<ResultDto<ResponseBookChapterContentDto>> bookChapterContentSave(@RequestBody RequestBookChapterContentDto requestBookChapterContentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.bookChapterContentSave(requestBookChapterContentDto));
    }

    @PutMapping("/book/chapter/content")
    public ResponseEntity<ResultDto<ResponseBookChapterContentDto>> updateBookContent(@RequestBody RequestBookChapterContentDto requestBookChapterContentDto){
        return ResponseEntity.ok().body(bookService.updateBookContent(requestBookChapterContentDto));
    }

    @GetMapping("/book/chapter/list")
    public ResponseEntity<ResultDto<List<ResponseBookChapterContentDto>>> bookChapterListSearch(@RequestParam Long bookInfoId){
        return ResponseEntity.ok().body(bookService.bookChapterListSearch(bookInfoId));
    }

    @GetMapping("/book/chapter/{bookChapterInfoId}")
    public ResponseEntity<ResultDto<ResponseBookChapterContentDto>> bookChapterSearch(@PathVariable Long bookChapterInfoId){
        return ResponseEntity.ok().body(bookService.bookChapterSearch(bookChapterInfoId));
    }

    @DeleteMapping("/book/chapter/{bookChapterInfoId}")
    public ResponseEntity<ResultDto<ResponseBookChapterContentDto>> deleteBookChapter(@PathVariable Long bookChapterInfoId){
        return ResponseEntity.ok().body(bookService.deleteBookChapter(bookChapterInfoId));
    }
}
