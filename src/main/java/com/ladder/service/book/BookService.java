package com.ladder.service.book;

import com.ladder.domain.book.BookInfo;
import com.ladder.dto.book.RequestBookInfoDto;
import com.ladder.dto.book.ResponseBookInfoDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.book.BookInfoRepository;
import com.ladder.util.FileUtil;
import com.ladder.vo.file.FileResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookInfoRepository bookInfoRepository;
    private final FileUtil fileUtil;

    public ResultDto<ResponseBookInfoDto> bookInfoSave(RequestBookInfoDto bookInfoDto) {
        try{
            FileResultVo fileResultVo = fileUtil.transferFile(bookInfoDto.getBookImgFile()); // 파일 저장
            BookInfo bookInfo = new BookInfo(bookInfoDto);
            if(fileResultVo.getResult() == 1){ // 파일 저장 성공시 파일 저장 경로 추가
                bookInfo.setBookImgUrl(fileResultVo.getFilePath());
            }

            bookInfoRepository.save(bookInfo);

            return ResultDto.of("success", new ResponseBookInfoDto(bookInfo));
        }catch (Exception e){
            return ResultDto.of("fail", new ResponseBookInfoDto());
        }
    }

    public ResultDto<List<ResponseBookInfoDto>> bookInfoListSearch() {
        try {
            List<ResponseBookInfoDto> bookInfoList = bookInfoRepository.findAll()
                    .stream().map(ResponseBookInfoDto::new).collect(Collectors.toList());
            System.out.println(bookInfoList.size());
            return ResultDto.of("success", bookInfoList);
        }catch (Exception e){
            return ResultDto.of("fail", new ArrayList<ResponseBookInfoDto>());
        }
    }
}
