package com.ladder.repository.edu;

import com.ladder.domain.edu.EduCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduCategoryRepository extends JpaRepository<EduCategory, Long> {
}
