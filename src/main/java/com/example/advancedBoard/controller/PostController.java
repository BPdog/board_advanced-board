package com.example.advancedBoard.controller;

import com.example.advancedBoard.dto.request.PostCreateRequest;
import com.example.advancedBoard.dto.request.PostUpdateRequest;
import com.example.advancedBoard.dto.response.PostDetailResponse;
import com.example.advancedBoard.dto.response.PostListResponse;
import com.example.advancedBoard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     * POST /api/posts
     */
    @PostMapping
    public ResponseEntity<PostDetailResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
        PostDetailResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 게시글 상세 조회
     * GET /api/posts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long id) {
        PostDetailResponse response = postService.getPostDetail(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 게시글 목록 조회
     * GET /api/posts
     */
    @GetMapping
    public ResponseEntity<List<PostListResponse>> getAllPosts() {
        List<PostListResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 게시판의 게시글 목록 조회
     * GET /api/posts/board/{boardId}
     */
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<PostListResponse>> getPostsByBoard(@PathVariable Long boardId) {
        List<PostListResponse> responses = postService.getPostsByBoard(boardId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 특정 사용자의 게시글 목록 조회
     * GET /api/posts/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostListResponse>> getPostsByUser(@PathVariable Long userId) {
        List<PostListResponse> responses = postService.getPostsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    /**
     * 제목으로 검색
     * GET /api/posts/search/title?keyword=검색어
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<PostListResponse>> searchByTitle(@RequestParam String keyword) {
        List<PostListResponse> responses = postService.searchByTitle(keyword);
        return ResponseEntity.ok(responses);
    }

    /**
     * 내용으로 검색
     * GET /api/posts/search/content?keyword=검색어
     */
    @GetMapping("/search/content")
    public ResponseEntity<List<PostListResponse>> searchByContent(@RequestParam String keyword) {
        List<PostListResponse> responses = postService.searchByContent(keyword);
        return ResponseEntity.ok(responses);
    }

    /**
     * 제목 또는 내용으로 검색
     * GET /api/posts/search?keyword=검색어
     */
    @GetMapping("/search")
    public ResponseEntity<List<PostListResponse>> searchPosts(@RequestParam String keyword) {
        List<PostListResponse> responses = postService.searchPosts(keyword);
        return ResponseEntity.ok(responses);
    }

    /**
     * 게시글 수정
     * PUT /api/posts/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDetailResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request) {
        PostDetailResponse response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 삭제
     * DELETE /api/posts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
