package com.example.advancedBoard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponse {

    private Long id;
    private String title;
    private String content;       // 전체 내용 포함
    private Long userId;
    private String username;
    private Long boardId;
    private String boardName;
    private Integer commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 추가 정보
    private List<CommentResponse> comments;  // 댓글 목록 포함 가능
}
