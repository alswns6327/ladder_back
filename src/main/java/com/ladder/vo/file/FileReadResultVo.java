package com.ladder.vo.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class FileReadResultVo {
    private int result;
    private String msg;
    private String imgBase64;
}
