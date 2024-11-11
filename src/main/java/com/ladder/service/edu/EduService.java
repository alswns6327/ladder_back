package com.ladder.service.edu;

import com.ladder.domain.edu.EduCategory;
import com.ladder.domain.edu.EduSubCategory;
import com.ladder.domain.edu.EducationalMaterials;
import com.ladder.dto.common.ResultDto;
import com.ladder.dto.edu.*;
import com.ladder.repository.edu.EduCategoryRepository;
import com.ladder.repository.edu.EduSubCategoryRepository;
import com.ladder.repository.edu.EducationalMaterialsRepository;
import com.ladder.util.DecodeSearchParam;
import com.ladder.vo.edu.EduSearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class EduService {

    private final EducationalMaterialsRepository educationalMaterialsRepository;
    private final EduCategoryRepository eduCategoryRepository;
    private final EduSubCategoryRepository eduSubCategoryRepository;

    public ResultDto<List<ResponseEduCategpryDto>> searchEduCategoryList(String userId) {
        try {
            List<ResponseEduCategpryDto> responseEduCategpryDtos =
                    eduCategoryRepository.findByFirstSaveUserAndDelYn(userId, 1).stream()
                            .map((eduCategory -> { List<ResponseEduSubCategoryDto> responseEduSubCategoryDtos =
                                    eduCategory.getEduSubCategories().stream()
                                            .filter(subCategory -> subCategory.getDelYn() != 0) // JPQL로 수정 예정
                                            .map(ResponseEduSubCategoryDto::new)
                                            .collect(Collectors.toList());
                                return new ResponseEduCategpryDto(eduCategory, responseEduSubCategoryDtos);
                            }))
                            .collect(Collectors.toList());

            return ResultDto.of("success", responseEduCategpryDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ArrayList<ResponseEduCategpryDto>());
        }
    }

    public ResultDto<ResponseEduCategpryDto> saveEduCategory(RequestEduCategoryDto requestEduCategoryDto) {
        try {
            EduCategory eduCategory = new EduCategory(requestEduCategoryDto);
            eduCategory = eduCategoryRepository.save(eduCategory);

            return ResultDto.of("success", new ResponseEduCategpryDto(eduCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduCategpryDto());
        }
    }

    public ResultDto<ResponseEduCategpryDto> updateEduCategory(RequestEduCategoryDto requestEduCategoryDto) {
        try {
            EduCategory eduCategory = eduCategoryRepository.findById(requestEduCategoryDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + requestEduCategoryDto.getCategorySeq()));

            eduCategory.updateAll(requestEduCategoryDto);
            return ResultDto.of("success", new ResponseEduCategpryDto(eduCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduCategpryDto());
        }
    }


    public ResultDto<ResponseEduCategpryDto> deleteEduCategory(Long categorySeq) {
        try {
            EduCategory eduCategory = eduCategoryRepository.findById(categorySeq)
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + categorySeq));

            eduCategory.remove();
            return ResultDto.of("success", new ResponseEduCategpryDto(eduCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduCategpryDto());
        }
    }

    public ResultDto<ResponseEduSubCategoryDto> saveEduSubCategory(RequestEduSubCategoryDto requestEduSubCategoryDto) {
        try {
            EduCategory eduCategory = eduCategoryRepository.findById(requestEduSubCategoryDto.getCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq: " + requestEduSubCategoryDto.getCategorySeq()));
            EduSubCategory eduSubCategory = new EduSubCategory(requestEduSubCategoryDto, eduCategory);
            eduSubCategory = eduSubCategoryRepository.save(eduSubCategory);
            return ResultDto.of("success", new ResponseEduSubCategoryDto(eduSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduSubCategoryDto());
        }
    }

    public ResultDto<ResponseEduSubCategoryDto> updateEduSubCategory(RequestEduSubCategoryDto requestEduSubCategoryDto) {
        try {
            EduSubCategory eduSubCategory = eduSubCategoryRepository.findById(requestEduSubCategoryDto.getSubCategorySeq())
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestEduSubCategoryDto.getSubCategorySeq()));

            eduSubCategory.updateAll(requestEduSubCategoryDto);
            return ResultDto.of("success", new ResponseEduSubCategoryDto(eduSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduSubCategoryDto());
        }
    }

    public ResultDto<ResponseEduSubCategoryDto> deleteEduSubCategory(Long subCategorySeq) {
        try {
            EduSubCategory eduSubCategory = eduSubCategoryRepository.findById(subCategorySeq)
                    .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + subCategorySeq));

            eduSubCategory.remove();
            return ResultDto.of("success", new ResponseEduSubCategoryDto(eduSubCategory));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduSubCategoryDto());
        }
    }

    public ResultDto<ResponseEduDto> saveEdu(RequestEduDto requestEduDto) {
        try {
            EduCategory eduCategory =
                    requestEduDto.getCategorySeq() == null ? null : eduCategoryRepository.findById(requestEduDto.getCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + requestEduDto.getCategorySeq()));
            EduSubCategory eduSubCategory =
                    requestEduDto.getSubCategorySeq() == null ? null : eduSubCategoryRepository.findById(requestEduDto.getSubCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestEduDto.getSubCategorySeq()));

            EducationalMaterials educationalMaterials = new EducationalMaterials(requestEduDto, eduCategory, eduSubCategory);

            educationalMaterialsRepository.save(educationalMaterials);

            return ResultDto.of("success", new ResponseEduDto(educationalMaterials));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduDto());
        }
    }

    public ResultDto<ResponseEduDto> updateEdu(RequestEduDto requestEduDto) {
        try {
            EducationalMaterials educationalMaterials = educationalMaterialsRepository.findById(requestEduDto.getEduSeq())
                    .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다.  eduSeq" + requestEduDto.getEduSeq()));
            EduCategory eduCategory =
                    requestEduDto.getCategorySeq() == null ? null : eduCategoryRepository.findById(requestEduDto.getCategorySeq())
                            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + requestEduDto.getCategorySeq()));
            EduSubCategory eduSubCategory =
                    requestEduDto.getSubCategorySeq() == null ? null : eduSubCategoryRepository.findById(requestEduDto.getSubCategorySeq())
                            .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + requestEduDto.getSubCategorySeq()));

            educationalMaterials.updateAll(requestEduDto, eduCategory, eduSubCategory);

            return ResultDto.of("success", new ResponseEduDto(educationalMaterials));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduDto());
        }
    }

    public ResultDto<List<ResponseEduDto>> searchEduList(String searchParam) {
        try {
            EduSearchParam eduSearchParam = DecodeSearchParam.decodeSearchParam(searchParam, EduSearchParam.class);

            List<EducationalMaterials> edus;
            if(eduSearchParam.getCategorySeq() != null) {
                EduCategory eduCategory = eduCategoryRepository.findById(eduSearchParam.getCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. categorySeq : " + eduSearchParam.getCategorySeq()));
                edus = educationalMaterialsRepository.findByFirstSaveUserAndEduCategoryAndDelYn(eduSearchParam.getLadderAccountId(), eduCategory, 1);
            }else if(eduSearchParam.getSubCategorySeq() != null) {
                EduSubCategory eduSubCategory = eduSubCategoryRepository.findById(eduSearchParam.getSubCategorySeq())
                        .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다. subCategorySeq : " + eduSearchParam.getSubCategorySeq()));
                edus = educationalMaterialsRepository.findByFirstSaveUserAndEduSubCategoryAndDelYn(eduSearchParam.getLadderAccountId(), eduSubCategory, 1);
            }else {
                edus = educationalMaterialsRepository.findByFirstSaveUserAndDelYn(eduSearchParam.getLadderAccountId(), 1);
            }


            List<ResponseEduDto> responseEduDtos = edus.stream()
                    .map(ResponseEduDto::new).collect(Collectors.toList());

            return ResultDto.of("success", responseEduDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ArrayList<ResponseEduDto>());
        }
    }

    public ResultDto<ResponseEduDto> searchEdu(Long eduSeq) {
        try {
            EducationalMaterials educationalMaterials = educationalMaterialsRepository.findById(eduSeq)
                    .orElseThrow(() -> new IllegalArgumentException("글 목록을 찾을 수 없습니다 : eduSeq : " + eduSeq));

            return ResultDto.of("success", new ResponseEduDto(educationalMaterials));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduDto());
        }
    }

    public ResultDto<ResponseEduDto> deleteEdu(Long eduSeq) {
        try {
            EducationalMaterials educationalMaterials = educationalMaterialsRepository.findById(eduSeq)
                    .orElseThrow(() -> new IllegalArgumentException("글 목록을 찾을 수 없습니다 : eduSeq : " + eduSeq));

            educationalMaterials.remove();
            return ResultDto.of("success", new ResponseEduDto(educationalMaterials));
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.of("fail", new ResponseEduDto());
        }
    }
}
