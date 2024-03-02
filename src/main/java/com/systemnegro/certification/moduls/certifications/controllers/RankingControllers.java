package com.systemnegro.certification.moduls.certifications.controllers;

import com.systemnegro.certification.moduls.certifications.useCases.Top10RankingUseCase;
import com.systemnegro.certification.moduls.students.entities.CertificationStudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingControllers {
    @Autowired
    private Top10RankingUseCase top10RankingUseCase;

    @GetMapping("/top10")
    public List<CertificationStudentEntity> top10() {
        return top10RankingUseCase.execute();
    }
}
