package com.rikkeisoft.userservice.controller;

import com.rikkeisoft.userservice.dto.QuizDTO;
import com.rikkeisoft.userservice.service.grpc.QuizServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {
    @Autowired
    private QuizServiceClient quizServiceClient;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDTO question) {
        try {
            Object response = quizServiceClient.createQuiz(question);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuiz(@RequestBody QuizDTO question) {
        try {
            Object response = quizServiceClient.updateQuiz(question);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getDetailQuiz(@RequestParam(name = "quizId", required = true) String quizId) {
        try {
            Object response = quizServiceClient.getQuizDetail(quizId);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> getListQuizByUser(@RequestParam Long userId) {
        try {
            Object response = quizServiceClient.getQuizListByUser(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> createQuiz(@RequestParam(name = "quizId") String quizId) {
        try {
            QuizDTO quizDTO = QuizDTO.builder().id(quizId).createdBy(1L).build();
            Object response = quizServiceClient.deleteQuiz(quizDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
