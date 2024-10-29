package com.ladder.repository.edu;

import com.ladder.domain.edu.EduSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduSubCategoryRepository extends JpaRepository<EduSubCategory, Long> {
}
