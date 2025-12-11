package com.example.advancedBoard.service;

import com.example.advancedBoard.dto.request.BoardCreateRequest;
import com.example.advancedBoard.dto.request.BoardUpdateRequest;
import com.example.advancedBoard.dto.response.BoardResponse;
import com.example.advancedBoard.entity.Board;
import com.example.advancedBoard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostService postService;

    /**
     * 게시판 생성
     */
    public BoardResponse createBoard(BoardCreateRequest request) {
        // 게시판명 중복 확인
        validateBoardNameDuplication(request.getName());

        Board board = Board.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Board savedBoard = boardRepository.save(board);
        return convertToResponse(savedBoard);
    }

    /**
     * 게시판 조회 (ID)
     */
    @Transactional(readOnly = true)
    public BoardResponse getBoardById(Long id) {
        Board board = findBoardEntityById(id);
        return convertToResponse(board);
    }

    /**
     * 게시판 조회 (이름)
     */
    @Transactional(readOnly = true)
    public BoardResponse getBoardByName(String name) {
        Board board = boardRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다: " + name));

        return convertToResponse(board);
    }

    /**
     * 전체 게시판 조회
     */
    @Transactional(readOnly = true)
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 게시판 수정
     */
    public BoardResponse updateBoard(Long id, BoardUpdateRequest request) {
        Board board = findBoardEntityById(id);

        // 게시판명 수정
        if (request.getName() != null && !request.getName().isEmpty()) {
            // 변경하는 경우에만 중복 확인
            if (!board.getName().equals(request.getName())) {
                validateBoardNameDuplication(request.getName());
            }
            board.updateName(request.getName());
        }

        // 설명 수정
        if (request.getDescription() != null) {
            board.updateDescription(request.getDescription());
        }

        Board savedBoard = boardRepository.save(board);
        return convertToResponse(savedBoard);
    }

    /**
     * 게시판 삭제
     */
    public void deleteBoard(Long id) {
        Board board = findBoardEntityById(id);

        // 게시판에 게시글이 있는지 확인
        long postCount = postService.countByBoard(board);
        if (postCount > 0) {
            throw new IllegalStateException("게시글이 존재하는 게시판은 삭제할 수 없습니다.");
        }

        boardRepository.delete(board);
    }

    // ========== Private 헬퍼 메서드 ==========

    /**
     * 게시판명 중복 확인
     */
    private void validateBoardNameDuplication(String name) {
        if (boardRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 게시판명입니다: " + name);
        }
    }

    /**
     * Entity -> Response 변환
     */
    private BoardResponse convertToResponse(Board board) {
        int postCount = (int) postService.countByBoard(board);

        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .postCount(postCount)
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    // ========== Public 헬퍼 메서드 (다른 Service에서 사용) ==========

    /**
     * Board Entity 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public Board findBoardEntityById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다. ID: " + id));
    }
}
