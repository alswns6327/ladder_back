package com.ladder.service.book;

import com.ladder.dto.book.RequestBookInfoDto;
import com.ladder.dto.book.ResponseBookInfoDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.book.BookInfoRepository;
import com.ladder.service.common.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookInfoRepository bookInfoRepository;
    private final FileService fileService;

    public ResultDto<ResponseBookInfoDto> bookInfoSave(RequestBookInfoDto bookInfoDto) {
        try{
            fileService.transferFile(bookInfoDto.getBookImgFile());
            return ResultDto.of("success", new ResponseBookInfoDto(""));
        }catch (Exception e){
            return ResultDto.of("fail", new ResponseBookInfoDto());
        }
    }
}
