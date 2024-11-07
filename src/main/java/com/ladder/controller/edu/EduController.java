package com.ladder.controller.edu;

import com.ladder.dto.edu.*;
import com.ladder.dto.common.ResultDto;
import com.ladder.dto.edu.*;
import com.ladder.service.edu.EduService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EduController {

    private final EduService eduService;

    @GetMapping("/edu/category/list")
    public ResponseEntity<ResultDto<List<ResponseEduCategpryDto>>> searchEduCategoryList(@RequestParam String userId){
        return ResponseEntity.ok().body(eduService.searchEduCategoryList(userId));
    }

    @PostMapping("/edu/category")
    public ResponseEntity<ResultDto<ResponseEduCategpryDto>> saveEduCategory(@RequestBody RequestEduCategoryDto requestEduCategoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(eduService.saveEduCategory(requestEduCategoryDto));
    }

    @PutMapping("/edu/category")
    public ResponseEntity<ResultDto<ResponseEduCategpryDto>> updateEduCategory(@RequestBody RequestEduCategoryDto requestEduCategoryDto){
        return ResponseEntity.ok().body(eduService.updateEduCategory(requestEduCategoryDto));
    }

    @DeleteMapping("/edu/category/{categorySeq}")
    public ResponseEntity<ResultDto<ResponseEduCategpryDto>> deleteEduCategory(@PathVariable Long categorySeq){
        return ResponseEntity.ok().body(eduService.deleteEduCategory(categorySeq));
    }

    @PostMapping("/edu/sub-category")
    public ResponseEntity<ResultDto<ResponseEduSubCategoryDto>> saveEduCategory(@RequestBody RequestEduSubCategoryDto requestEduSubCategoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(eduService.saveEduSubCategory(requestEduSubCategoryDto));
    }

    @PutMapping("/edu/sub-category")
    public ResponseEntity<ResultDto<ResponseEduSubCategoryDto>> updateEduCategory(@RequestBody RequestEduSubCategoryDto requestEduSubCategoryDto){
        return ResponseEntity.ok().body(eduService.updateEduSubCategory(requestEduSubCategoryDto));
    }

    @DeleteMapping("/edu/sub-category/{categorySeq}")
    public ResponseEntity<ResultDto<ResponseEduSubCategoryDto>> updateEduCategory(@PathVariable Long categorySeq){
        return ResponseEntity.ok().body(eduService.deleteEduSubCategory(categorySeq));
    }

    @PostMapping("/edu")
    public ResponseEntity<ResultDto<ResponseEduDto>> saveEdu(@RequestBody RequestEduDto requestEduDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eduService.saveEdu(requestEduDto));
    }

    @PutMapping("/edu")
    public ResponseEntity<ResultDto<ResponseEduDto>> updateEdu(@RequestBody RequestEduDto requestEduDto) {
        return ResponseEntity.ok().body(eduService.updateEdu(requestEduDto));
    }

    @GetMapping("/edu")
    public ResponseEntity<ResultDto<List<ResponseEduDto>>> searchEduList(@RequestParam String searchParam){
        return ResponseEntity.ok().body(eduService.searchEduList(searchParam));
    }

    @GetMapping("/edu/{eduSeq}")
    public ResponseEntity<ResultDto<ResponseEduDto>> searchEdu(@PathVariable Long eduSeq){
        return ResponseEntity.ok().body(eduService.searchEdu(eduSeq));
    }

}
