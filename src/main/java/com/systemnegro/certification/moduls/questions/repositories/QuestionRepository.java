package com.systemnegro.certification.moduls.questions.repositories;

import com.systemnegro.certification.moduls.questions.entities.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionsEntity, UUID> {
    List<QuestionsEntity> findByTechnology(String technology);
}
