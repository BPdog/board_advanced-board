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
public class CommentCreateRequest {

    @NotBlank(message = "댓글 내용은 필수입니다")
    @Size(min = 1, max = 500, message = "댓글은 1-500자여야 합니다")
    private String content;

    @NotNull(message = "게시글 ID는 필수입니다")
    private Long postId;

    @NotNull(message = "작성자 ID는 필수입니다")
    private Long userId;
}
