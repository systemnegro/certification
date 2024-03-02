package com.systemnegro.certification.moduls.students.repositories;

import com.systemnegro.certification.moduls.students.entities.CertificationStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CertificationStudentRepository extends JpaRepository<CertificationStudentEntity, UUID> {
    @Query("SELECT c FROM certifications c INNER JOIN c.studentEntity std where std.email = :email AND c.technology = :technology")
    List<CertificationStudentEntity> findByStudentEmailAndTechnology(String email, String technology);
    @Query("SELECT c FROM certifications c ORDER BY c.grade DESC LIMIT 10")
    List<CertificationStudentEntity> findByTop10ByOrderByGradeDesc();
}
