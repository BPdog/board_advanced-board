package com.example.advancedBoard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long postId;
    private String postTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
