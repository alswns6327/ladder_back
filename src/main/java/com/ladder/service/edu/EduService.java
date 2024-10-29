package com.ladder.service.edu;

import com.ladder.repository.edu.EduCategoryRepository;
import com.ladder.repository.edu.EduSubCategoryRepository;
import com.ladder.repository.edu.EducationalMaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EduService {

    private final EducationalMaterialsRepository educationalMaterialsRepository;
    private final EduCategoryRepository eduCategoryRepository;
    private final EduSubCategoryRepository eduSubCategoryRepository;

}
