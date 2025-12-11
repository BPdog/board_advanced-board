package com.example.advancedBoard.controller;

import com.example.advancedBoard.dto.request.BoardCreateRequest;
import com.example.advancedBoard.dto.request.BoardUpdateRequest;
import com.example.advancedBoard.dto.response.BoardResponse;
import com.example.advancedBoard.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시판 생성
     * POST /api/boards
     */
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardCreateRequest request) {
        BoardResponse response = boardService.createBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 게시판 조회 (ID)
     * GET /api/boards/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long id) {
        BoardResponse response = boardService.getBoardById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시판 조회 (이름)
     * GET /api/boards/search/name?name=자유게시판
     */
    @GetMapping("/search/name")
    public ResponseEntity<BoardResponse> findByName(@RequestParam String name) {
        return boardService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 전체 게시판 조회
     * GET /api/boards
     */
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> responses = boardService.getAllBoards();
        return ResponseEntity.ok(responses);
    }

    /**
     * 게시판 수정
     * PUT /api/boards/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable Long id,
            @Valid @RequestBody BoardUpdateRequest request) {
        BoardResponse response = boardService.updateBoard(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 게시판 삭제
     * DELETE /api/boards/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
