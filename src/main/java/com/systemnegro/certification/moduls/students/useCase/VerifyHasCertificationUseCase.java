package com.systemnegro.certification.moduls.students.useCase;

import com.systemnegro.certification.moduls.students.dto.VerifyHasCertificationDTO;
import com.systemnegro.certification.moduls.students.repositories.CertificationStudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public boolean execute(VerifyHasCertificationDTO dto) {
        var result = this.certificationStudentRepository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        return !result.isEmpty();
    }
}
