package com.ladder.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBookInfoDto {

    private Long bookInfoId;
    private String bookName;
    private String bookDescription;
    private String bookAuthorName;
    private String bookTranslatorName;
    private MultipartFile bookImgFile;
}
