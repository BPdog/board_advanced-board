package com.example.advancedBoard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Size(min = 3, max = 20, message = "사용자명은 3-20자여야 합니다")
    private String username;

    // 이메일은 로그인 ID로 사용 수정 불가능하므로 제외

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    private String password;
}
