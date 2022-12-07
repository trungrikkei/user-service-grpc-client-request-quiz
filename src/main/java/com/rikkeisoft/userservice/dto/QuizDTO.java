package com.rikkeisoft.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDTO {
    private String id;
    private String question;
    private List<ChoiceDTO> choices;
    private Integer answer;
    private Integer difficulty;
    private Long createdBy;
    private Timestamp createdAt;
}
