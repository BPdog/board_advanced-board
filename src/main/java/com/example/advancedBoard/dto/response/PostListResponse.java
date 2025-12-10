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
public class PostListResponse {

    private Long id;
    private String title;
    private String username;      // 작성자명만
    private String boardName;     // 게시판명만
    private Integer commentCount; // 댓글 수
    private LocalDateTime createdAt;

    // content는 제외 (목록에서는 불필요)
}
