package com.rikkeisoft.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteQuizResponse {
    private String quizId;
    private Long createdBy;
    boolean isSuccess;
}
