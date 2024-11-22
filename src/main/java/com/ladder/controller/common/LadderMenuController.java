package com.ladder.controller.common;

import com.ladder.dto.common.ResponseMenuDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.service.common.LadderMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LadderMenuController {

    private final LadderMenuService ladderMenuService;

    @GetMapping("/menu")
    public ResponseEntity<ResultDto<List<ResponseMenuDto>>> searchMenuList(){
        return ResponseEntity.ok().body(ladderMenuService.searchMenuList());
    }

}
