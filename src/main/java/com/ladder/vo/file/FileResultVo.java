package com.ladder.vo.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class FileResultVo {
    private int result;
    private String msg;
    private String filePath;
}
