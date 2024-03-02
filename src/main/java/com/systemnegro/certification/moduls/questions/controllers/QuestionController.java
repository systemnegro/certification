package com.systemnegro.certification.moduls.questions.controllers;

import com.systemnegro.certification.moduls.questions.controllers.dto.AlternativesResultDTO;
import com.systemnegro.certification.moduls.questions.controllers.dto.QuestionResultDTO;
import com.systemnegro.certification.moduls.questions.entities.AlternativesEntity;
import com.systemnegro.certification.moduls.questions.entities.QuestionsEntity;
import com.systemnegro.certification.moduls.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.questionRepository.findByTechnology(technology);
        var toMap = result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());
        return toMap;
    }
    static QuestionResultDTO mapQuestionToDTO(QuestionsEntity questions){
        var questionsResultDTO = QuestionResultDTO.builder()
                .id(questions.getId())
                .technology(questions.getTechnology())
                .description(questions.getDescription()).build();
        List<AlternativesResultDTO> alternativesResultDTOs = questions.getAlternatives()
                .stream().map(alternative -> mapAlternativeDTO(alternative))
                .collect(Collectors.toList());

        questionsResultDTO.setAlternatives(alternativesResultDTOs);
        return questionsResultDTO;
    }

    static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternativesResultDTO){
        return AlternativesResultDTO.builder()
                .id(alternativesResultDTO.getId())
                .description(alternativesResultDTO.getDescription()).build();
    }
}
