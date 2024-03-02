package com.systemnegro.certification.moduls.students.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCertificationAnswersDTO {

    private String email;

    private String technology;

    private List<QuestionAnswersDTO> questionAnswers;
}
