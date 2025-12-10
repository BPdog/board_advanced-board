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
public class BoardResponse {

    private Long id;
    private String name;
    private String description;
    private Integer postCount;  // 게시글 수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
