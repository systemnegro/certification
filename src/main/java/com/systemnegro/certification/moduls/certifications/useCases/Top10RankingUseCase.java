package com.systemnegro.certification.moduls.certifications.useCases;

import com.systemnegro.certification.moduls.students.entities.CertificationStudentEntity;
import com.systemnegro.certification.moduls.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Top10RankingUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    public List<CertificationStudentEntity> execute(){
        return this.certificationStudentRepository.findByTop10ByOrderByGradeDesc();

    }
}
