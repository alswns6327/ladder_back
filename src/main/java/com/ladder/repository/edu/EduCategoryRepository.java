package com.ladder.repository.edu;

import com.ladder.domain.edu.EduCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface EduCategoryRepository extends JpaRepository<EduCategory, Long> {
    List<EduCategory> findByFirstSaveUserAndDelYn(String userId, int i);
}
