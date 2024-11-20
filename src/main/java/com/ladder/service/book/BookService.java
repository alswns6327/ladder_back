package com.ladder.service.book;

import com.ladder.domain.book.BookChapterInfo;
import com.ladder.domain.book.BookInfo;
import com.ladder.dto.book.RequestBookChapterContentDto;
import com.ladder.dto.book.RequestBookInfoDto;
import com.ladder.dto.book.ResponseBookChapterContentDto;
import com.ladder.dto.book.ResponseBookInfoDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.book.BookChapterInfoRepository;
import com.ladder.repository.book.BookInfoRepository;
import com.ladder.util.CommonUtil;
import com.ladder.util.FileUtil;
import com.ladder.vo.file.FileReadResultVo;
import com.ladder.vo.file.FileUploadResultVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService {

    private final BookInfoRepository bookInfoRepository;
    private final BookChapterInfoRepository bookChapterInfoRepository;
    private final FileUtil fileUtil;

    public ResultDto<ResponseBookInfoDto> bookInfoSave(RequestBookInfoDto bookInfoDto) {
        try{
            FileUploadResultVo fileUploadResultVo = fileUtil.transferFile(bookInfoDto.getBookImgFile()); // 파일 저장
            BookInfo bookInfo = new BookInfo(bookInfoDto);
            if(fileUploadResultVo.getResult() == 1){ // 파일 저장 성공시 파일 저장 경로 추가
                String filePath = fileUploadResultVo.getFilePath();
                String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
                bookInfo.setBookImgUrl(filePath);
                bookInfo.setBookImgFileExtension(fileExtension);
            }

            bookInfoRepository.save(bookInfo);

            return ResultDto.of("success", "200", new ResponseBookInfoDto(bookInfo));
        }catch (Exception e){
            return ResultDto.of("fail", "400", new ResponseBookInfoDto());
        }
    }

    public ResultDto<List<ResponseBookInfoDto>> bookInfoListSearch(String ladderAccountId) {
        try {
            FTPClient ftpClient = fileUtil.connectFTPClient();
            if (!ftpClient.isConnected() || !ftpClient.isAvailable()) throw new IOException("ftp 연결 실패");
            List<ResponseBookInfoDto> responseBookInfoDtos = bookInfoRepository.findByFirstSaveUserAndDelYn(ladderAccountId, 0).stream().map(
                                                                bookInfo -> new ResponseBookInfoDto(bookInfo, fileUtil.readImgFile(ftpClient, bookInfo.getBookImgUrl()))
                                                            ).collect(Collectors.toList());
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            return ResultDto.of("success", "200", responseBookInfoDtos);
        }catch (Exception e){
            return ResultDto.of("fail", "400", new ArrayList<ResponseBookInfoDto>());
        }
    }

    public ResultDto<ResponseBookInfoDto> searchBookInfo(Long bookInfoId) {
        try {
            BookInfo bookInfo = bookInfoRepository.findById(bookInfoId)
                    .orElseThrow(() -> new IllegalArgumentException("책을 찾지 못하였음 : " + bookInfoId));
            FileReadResultVo fileReadResultVo = fileUtil.readImgFile(bookInfo.getBookImgUrl());
            return ResultDto.of("success", "200", new ResponseBookInfoDto(bookInfo, fileReadResultVo.getImgBase64()));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookInfoDto());
        }
    }

    public ResultDto<ResponseBookInfoDto> updateBookInfo(RequestBookInfoDto bookInfoDto) {
        try {
            BookInfo bookInfo = bookInfoRepository.findById(bookInfoDto.getBookInfoId())
                    .orElseThrow(() -> new IllegalArgumentException("책을 찾지 못하였음 : " + bookInfoDto.getBookInfoId()));
            FileUploadResultVo fileUploadResultVo = fileUtil.transferFile(bookInfoDto.getBookImgFile()); // 파일 저장
            if(fileUploadResultVo.getResult() == 1){ // 파일 저장 성공시 파일 저장 경로 추가
                String filePath = fileUploadResultVo.getFilePath();
                String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
                bookInfo.setBookImgUrl(filePath);
                bookInfo.setBookImgFileExtension(fileExtension);
            }
            bookInfo.updateAll(bookInfoDto);
            return ResultDto.of("success", "200", new ResponseBookInfoDto(bookInfo));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookInfoDto());
        }
    }


    public ResultDto<ResponseBookInfoDto> deleteBookInfo(Long bookInfoId) {
        try {
            BookInfo bookInfo = bookInfoRepository.findById(bookInfoId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 책 정보를 찾을 수 없습니다. bookInfoId : " + bookInfoId));
            bookInfo.remove();
            return ResultDto.of("success", "200", new ResponseBookInfoDto(bookInfo));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookInfoDto());
        }
    }

    public ResultDto<ResponseBookChapterContentDto> bookChapterContentSave(RequestBookChapterContentDto requestBookChapterContentDto) {
        try {
            BookInfo bookInfo = bookInfoRepository.findById(requestBookChapterContentDto.getBookInfoId())
                    .orElseThrow(() -> new IllegalArgumentException("책 정보가 없습니다 bookInfoId : " + requestBookChapterContentDto.getBookInfoId()));
            BookChapterInfo bookChapterInfo = bookChapterInfoRepository.save(new BookChapterInfo(requestBookChapterContentDto, bookInfo));
            return ResultDto.of("success", "200", new ResponseBookChapterContentDto(bookChapterInfo));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookChapterContentDto());
        }
    }

    public ResultDto<ResponseBookChapterContentDto> updateBookContent(RequestBookChapterContentDto requestBookChapterContentDto){
        try {
            BookChapterInfo bookChapterInfo = bookChapterInfoRepository.findById(requestBookChapterContentDto.getBookChapterInfoId())
                    .orElseThrow(() -> new IllegalArgumentException("챕터 정보가 없습니다. bookChapterInfoId : " + requestBookChapterContentDto.getBookChapterInfoContent()));
            if(!bookChapterInfo.getFirstSaveUser().equals(CommonUtil.getLadderAccountId())) return ResultDto.of("fail", "400", new ResponseBookChapterContentDto());

            bookChapterInfo.updateAll(requestBookChapterContentDto);
            return ResultDto.of("success", "200", new ResponseBookChapterContentDto(bookChapterInfo));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookChapterContentDto());
        }
    }

    public ResultDto<List<ResponseBookChapterContentDto>> bookChapterListSearch(Long bookInfoId) {
        try {
            BookInfo bookInfo = bookInfoRepository.findById(bookInfoId)
                    .orElseThrow(() -> new IllegalArgumentException("책 정보가 없습니다. bookInfoId : " + bookInfoId));
            List<ResponseBookChapterContentDto> responseBookChapterContentDtos = bookChapterInfoRepository.findByBookInfoAndDelYn(bookInfo, 0).stream()
                    .map(ResponseBookChapterContentDto::new).collect(Collectors.toList());

            return ResultDto.of("success", "200", responseBookChapterContentDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ArrayList<ResponseBookChapterContentDto>());
        }
    }

    public ResultDto<ResponseBookChapterContentDto> bookChapterSearch(Long bookChapterInfoId) {
        try {
            BookChapterInfo bookChapterInfo = bookChapterInfoRepository.findById(bookChapterInfoId)
                    .orElseThrow(() -> new IllegalArgumentException("챕터 정보를 찾을 수 없습니다. bookChapterId : " + bookChapterInfoId));

            return ResultDto.of("success", "200", new ResponseBookChapterContentDto(bookChapterInfo));
        }catch (Exception e) {
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookChapterContentDto());
        }
    }

    public ResultDto<ResponseBookChapterContentDto> deleteBookChapter(Long bookChapterInfoId) {
        try {
            BookChapterInfo bookChapterInfo = bookChapterInfoRepository.findById(bookChapterInfoId)
                    .orElseThrow(() -> new IllegalArgumentException("챕터 정보를 찾을 수 없습니다. bookChapterId : " + bookChapterInfoId));

            bookChapterInfo.remove();
            return ResultDto.of("success", "200", new ResponseBookChapterContentDto(bookChapterInfo));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ResponseBookChapterContentDto());
        }
    }
}
