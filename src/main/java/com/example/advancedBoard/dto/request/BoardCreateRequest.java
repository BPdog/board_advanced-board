package com.example.advancedBoard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

    @NotBlank(message = "게시판명은 필수입니다")
    @Size(min = 2, max = 50, message = "게시판명은 2-50자여야 합니다")
    private String name;

    @Size(max = 200, message = "설명은 200자 이하여야 합니다")
    private String description;
}
