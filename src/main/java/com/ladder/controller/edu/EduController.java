package com.ladder.controller.edu;

import com.ladder.service.edu.EduService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EduController {

    private final EduService eduService;

}
