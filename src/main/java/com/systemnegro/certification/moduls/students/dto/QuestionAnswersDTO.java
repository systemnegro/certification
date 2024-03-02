package com.systemnegro.certification.moduls.students.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswersDTO {

    private UUID questionID;
    private UUID alternativeID;
    private boolean isCorrect;
}
