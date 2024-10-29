package com.ladder.repository.edu;

import com.ladder.domain.edu.EducationalMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalMaterialsRepository extends JpaRepository<EducationalMaterials, Long> {
}
