package com.example.advancedBoard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 2, max = 100, message = "제목은 2-100자여야 합니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    @Size(min = 1, max = 5000, message = "내용은 1-5000자여야 합니다")
    private String content;

    @NotNull(message = "게시판 ID는 필수입니다")
    private Long boardId;

    @NotNull(message = "작성자 ID는 필수입니다")
    private Long userId;
}
