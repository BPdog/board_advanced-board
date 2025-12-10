package com.example.advancedBoard.controller;

import com.example.advancedBoard.dto.request.UserCreateRequest;
import com.example.advancedBoard.dto.request.UserUpdateRequest;
import com.example.advancedBoard.dto.response.UserResponse;
import com.example.advancedBoard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 생성
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 사용자 조회 (ID)
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 조회 (이메일)
     * GET /api/users/email?email=test@example.com
     */
    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        UserResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 사용자 조회
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getAllUsers();
        return ResponseEntity.ok(responses);
    }

    /**
     * 사용자 수정
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 삭제 (논리적 삭제)
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 사용자 차단
     * POST /api/users/{id}/block
     */
    @PostMapping("/{id}/block")
    public ResponseEntity<UserResponse> blockUser(@PathVariable Long id) {
        UserResponse response = userService.blockUser(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 활성화
     * POST /api/users/{id}/activate
     */
    @PostMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable Long id) {
        UserResponse response = userService.activateUser(id);
        return ResponseEntity.ok(response);
    }
}
