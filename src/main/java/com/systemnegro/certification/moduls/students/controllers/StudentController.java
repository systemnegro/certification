package com.systemnegro.certification.moduls.students.controllers;

import com.systemnegro.certification.moduls.students.dto.StudentCertificationAnswersDTO;
import com.systemnegro.certification.moduls.students.dto.VerifyHasCertificationDTO;
import com.systemnegro.certification.moduls.students.entities.CertificationStudentEntity;
import com.systemnegro.certification.moduls.students.repositories.CertificationStudentRepository;
import com.systemnegro.certification.moduls.students.useCase.StudentCertificationAnswersUseCase;
import com.systemnegro.certification.moduls.students.useCase.VerifyHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/verifyHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        var result = this.verifyHasCertificationUseCase.execute(verifyHasCertificationDTO);
        if (result) {
            return "Usuário ja fez a prova";
        }
        return "Usuário pode fazer a prova";
    }
    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswers(@RequestBody StudentCertificationAnswersDTO studentCertificationAnswersDTO)  {
        try {
            var result = studentCertificationAnswersUseCase.execute(studentCertificationAnswersDTO);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
