package com.ladder.controller.book;

import com.ladder.dto.book.RequestBookInfoDto;
import com.ladder.dto.book.ResponseBookInfoDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book/info")
    public ResponseEntity<ResultDto<ResponseBookInfoDto>> bookInfoSave (@ModelAttribute RequestBookInfoDto bookInfoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.bookInfoSave(bookInfoDto));
    }

}
