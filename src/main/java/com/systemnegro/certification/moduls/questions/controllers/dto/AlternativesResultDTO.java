package com.systemnegro.certification.moduls.questions.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlternativesResultDTO {

    private UUID id;

    private String description;
}
