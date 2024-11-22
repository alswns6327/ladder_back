package com.ladder.service.common;

import com.ladder.dto.common.ResponseMenuDto;
import com.ladder.dto.common.ResultDto;
import com.ladder.repository.common.LadderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LadderMenuService {

    private final LadderMenuRepository ladderMenuRepository;

    public ResultDto<List<ResponseMenuDto>> searchMenuList (){
        try {
            List<ResponseMenuDto> responseMenuDtos = ladderMenuRepository.findByDelYnOrderByLadderMenuOrderAsc(0).stream()
                    .map(ResponseMenuDto::new).collect(Collectors.toList());

            return ResultDto.of("success", "200", responseMenuDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", "400", new ArrayList<ResponseMenuDto>());
        }
    }
}
