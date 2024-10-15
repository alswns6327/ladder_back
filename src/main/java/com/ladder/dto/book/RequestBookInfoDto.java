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

    private String bookName;
    private String bookAuthorName;
    private String bookTranslatorName;
    private MultipartFile bookImgFile;
}
