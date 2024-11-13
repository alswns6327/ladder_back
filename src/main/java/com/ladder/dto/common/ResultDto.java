package com.ladder.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter
public class ResultDto<D> {

    private final String msg;
    private final String code;
    private final D data;
}
