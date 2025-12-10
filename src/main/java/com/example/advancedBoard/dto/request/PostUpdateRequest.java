package com.example.advancedBoard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {

    @Size(min = 2, max = 100, message = "제목은 2-100자여야 합니다")
    private String title;

    @Size(min = 1, max = 5000, message = "내용은 1-5000자여야 합니다")
    private String content;
}
