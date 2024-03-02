package com.systemnegro.certification.moduls.students.useCase;

import com.systemnegro.certification.moduls.questions.entities.AlternativesEntity;
import com.systemnegro.certification.moduls.questions.entities.QuestionsEntity;
import com.systemnegro.certification.moduls.questions.repositories.QuestionRepository;
import com.systemnegro.certification.moduls.students.dto.StudentCertificationAnswersDTO;
import com.systemnegro.certification.moduls.students.dto.VerifyHasCertificationDTO;
import com.systemnegro.certification.moduls.students.entities.AnswersCertificationsEntity;
import com.systemnegro.certification.moduls.students.entities.CertificationStudentEntity;
import com.systemnegro.certification.moduls.students.entities.StudentEntity;
import com.systemnegro.certification.moduls.students.repositories.CertificationStudentRepository;
import com.systemnegro.certification.moduls.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyHasCertificationUseCase verifyHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswersDTO dto) throws Exception {

        var hasCertification = this.verifyHasCertificationUseCase.execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification){
            throw new Exception("Você já tem essa certificação");
        }

        //Buscar alternativas

        List<QuestionsEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());

        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers().forEach(questionAnswer -> {
            var question = questionsEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
                    .findFirst().get();

            // pegar a alternativa correta
            var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(AlternativesEntity::isCorrect).findFirst().get();


            // questionAnswer.setCorrect(findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID()));

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answersCertificationEntity = AnswersCertificationsEntity.builder()
                    .answerID(questionAnswer.getAlternativeID())
                    .questionID(questionAnswer.getQuestionID())
                    .isCorrect(questionAnswer.isCorrect()).build();

            answersCertifications.add(answersCertificationEntity);


        });
        // Verificar se existe estudante pelo email
        UUID stutendID;
        var student = studentRepository.findByEmail(dto.getEmail());
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            stutendID = studentCreated.getId();
        } else {
            stutendID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentID(stutendID)
                .grade(correctAnswers.get())
                .build();
        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);


        answersCertifications.forEach(answerCertifications -> {
            answerCertifications.setCertificationID(certificationStudentEntity.getId());
            answerCertifications.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntity(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);


        return certificationStudentCreated;
    }
}
