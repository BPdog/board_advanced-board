package com.example.advancedBoard.service;

import com.example.advancedBoard.dto.request.CommentCreateRequest;
import com.example.advancedBoard.dto.request.CommentUpdateRequest;
import com.example.advancedBoard.dto.response.CommentResponse;
import com.example.advancedBoard.entity.Comment;
import com.example.advancedBoard.entity.Post;
import com.example.advancedBoard.entity.User;
import com.example.advancedBoard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    /**
     * 댓글 생성
     */
    public CommentResponse createComment(CommentCreateRequest request) {
        User user = userService.findUserEntityById(request.getUserId());
        Post post = postService.findPostEntityById(request.getPostId());

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return convertToResponse(savedComment);
    }

    /**
     * 댓글 조회 (ID)
     */
    @Transactional(readOnly = true)
    public CommentResponse getCommentById(Long id) {
        Comment comment = findCommentEntityById(id);
        return convertToResponse(comment);
    }

    /**
     * 전체 댓글 조회
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글의 댓글 목록 조회 (작성일순)
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPost(Long postId) {
        Post post = postService.findPostEntityById(postId);
        return commentRepository.findByPostOrderByCreatedAtAsc(post).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 댓글 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByUser(Long userId) {
        User user = userService.findUserEntityById(userId);
        return commentRepository.findByUser(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글의 댓글 수 조회
     */
    @Transactional(readOnly = true)
    public long getCommentCountByPost(Long postId) {
        Post post = postService.findPostEntityById(postId);
        return commentRepository.countByPost(post);
    }

    /**
     * 특정 사용자의 댓글 수 조회
     */
    @Transactional(readOnly = true)
    public long getCommentCountByUser(Long userId) {
        User user = userService.findUserEntityById(userId);
        return commentRepository.countByUser(user);
    }

    /**
     * 댓글 수정
     */
    public CommentResponse updateComment(Long id, CommentUpdateRequest request) {
        Comment comment = findCommentEntityById(id);

        // 내용 수정
        comment.updateContent(request.getContent());

        Comment savedComment = commentRepository.save(comment);
        return convertToResponse(savedComment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long id) {
        Comment comment = findCommentEntityById(id);
        commentRepository.delete(comment);
    }

    // ========== Private 헬퍼 메서드 ==========

    /**
     * Entity -> Response 변환
     */
    private CommentResponse convertToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .postId(comment.getPost().getId())
                .postTitle(comment.getPost().getTitle())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    // ========== Public 헬퍼 메서드 (다른 Service에서 사용) ==========

    /**
     * 특정 게시글의 댓글 수 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public long countByPost(Post post) {
        return commentRepository.countByPost(post);
    }

    /**
     * Comment Entity 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public Comment findCommentEntityById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다. ID: " + id));
    }
    /**
     * 특정 게시글의 댓글 목록 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public List<Comment> findCommentEntitiesByPost(Post post) {
        return commentRepository.findByPostOrderByCreatedAtAsc(post);
    }
}
