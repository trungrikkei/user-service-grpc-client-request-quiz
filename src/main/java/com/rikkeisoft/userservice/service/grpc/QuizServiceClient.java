package com.rikkeisoft.userservice.service.grpc;

import com.rikkeisoft.quiz.*;
import com.rikkeisoft.userservice.dto.ChoiceDTO;
import com.rikkeisoft.userservice.dto.DeleteQuizResponse;
import com.rikkeisoft.userservice.dto.QuizDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceClient {
    @GrpcClient("grpc-quiz-service")
    QuizServiceGrpc.QuizServiceBlockingStub quizServiceBlockingStub;

    public Object createQuiz(QuizDTO input) {
        input.setCreatedBy(1L);
        Quiz req = convertQuizDtoToQuiz(input);
        Quiz res = quizServiceBlockingStub.createQuiz(req);
        return convertQuizToQuizDto(res);
    }

    public Object getQuizDetail(String questionId) {
        QuizId quizId = QuizId.newBuilder()
                .setId(questionId)
                .build();
        Quiz quiz =  quizServiceBlockingStub.getDetailQuiz(quizId);
        return convertQuizToQuizDto(quiz);
    }

    public Object getQuizListByUser(Long userId) {
        UserId req = UserId.newBuilder().setUserId(userId).build();
        Quizzes quizzes = quizServiceBlockingStub.getQuizListByUser(req);
        List<QuizDTO> listQuiz = new ArrayList<>();
        quizzes.getQuizzesList().forEach(quiz -> {
            listQuiz.add(convertQuizToQuizDto(quiz));
        });
        return listQuiz;
    }

    public Object updateQuiz(QuizDTO quizDTO) {
        List<Choice> choices = new ArrayList<>();
        quizDTO.getChoices().forEach(choiceDTO -> {
            choices.add(Choice.newBuilder()
                    .setIndex(choiceDTO.getIndex())
                    .setChoice(choiceDTO.getChoice())
                    .build());
        });
        Quiz req = Quiz.newBuilder()
                .setId(quizDTO.getId())
                .setQuestion(quizDTO.getQuestion())
                .addAllChoices(choices)
                .setAnswer(quizDTO.getAnswer())
                .setDifficulty(quizDTO.getDifficulty())
                .setUserId(quizDTO.getCreatedBy())
                .build();
        Quiz quiz = quizServiceBlockingStub.updateQuiz(req);
        return convertQuizToQuizDto(quiz);
    }

    public Object deleteQuiz(QuizDTO quizDTO) {
        Quiz req = Quiz.newBuilder()
                .setId(quizDTO.getId())
                .setUserId(quizDTO.getCreatedBy())
                .build();
        DeleteOutput res = quizServiceBlockingStub.deleteQuiz(req);
        return DeleteQuizResponse.builder()
                .quizId(req.getId())
                .createdBy(res.getUserId())
                .isSuccess(res.getIsSuccess())
                .build();
    }

    private Quiz convertQuizDtoToQuiz(QuizDTO quizDTO) {
        List<Choice> choices = new ArrayList<>();
        quizDTO.getChoices().forEach(choiceDTO -> {
            choices.add(Choice.newBuilder()
                    .setIndex(choiceDTO.getIndex())
                    .setChoice(choiceDTO.getChoice())
                    .build());
        });
        return Quiz.newBuilder()
                .setQuestion(quizDTO.getQuestion())
                .addAllChoices(choices)
                .setAnswer(quizDTO.getAnswer())
                .setDifficulty(quizDTO.getDifficulty())
                .setUserId(quizDTO.getCreatedBy())
                .build();
    }

    private QuizDTO convertQuizToQuizDto(Quiz quiz) {
        List<ChoiceDTO> choiceDTOS = new ArrayList<>();
        quiz.getChoicesList().forEach(choice -> {
            choiceDTOS.add(new ChoiceDTO(choice.getIndex(), choice.getChoice()));
        });
        return QuizDTO.builder()
                .id(quiz.getId())
                .question(quiz.getQuestion())
                .choices(choiceDTOS)
                .answer(quiz.getAnswer())
                .difficulty(quiz.getDifficulty())
                .createdBy(quiz.getUserId())
                .build();
    }

}
