package com.example.advancedBoard.controller;

import com.example.advancedBoard.dto.request.CommentCreateRequest;
import com.example.advancedBoard.dto.request.CommentUpdateRequest;
import com.example.advancedBoard.dto.response.CommentResponse;
import com.example.advancedBoard.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     * POST /api/comments
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentCreateRequest request) {
        CommentResponse response = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 댓글 조회 (ID)
     * GET /api/comments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        CommentResponse response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 댓글 조회
     * GET /api/comments
     */
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        List<CommentResponse> responses = commentService.getAllComments();
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 게시글의 댓글 목록 조회
     * GET /api/comments/post/{postId}
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponse> responses = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 사용자의 댓글 목록 조회
     * GET /api/comments/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable Long userId) {
        List<CommentResponse> responses = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 게시글의 댓글 수 조회
     * GET /api/comments/post/{postId}/count
     */
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getCommentCountByPost(@PathVariable Long postId) {
        long count = commentService.getCommentCountByPost(postId);
        return ResponseEntity.ok(count);
    }

    /**
     * 특정 사용자의 댓글 수 조회
     * GET /api/comments/user/{userId}/count
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getCommentCountByUser(@PathVariable Long userId) {
        long count = commentService.getCommentCountByUser(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * 댓글 수정
     * PUT /api/comments/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdateRequest request) {
        CommentResponse response = commentService.updateComment(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 댓글 삭제
     * DELETE /api/comments/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
