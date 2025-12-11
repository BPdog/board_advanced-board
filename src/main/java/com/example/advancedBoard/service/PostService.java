package com.example.advancedBoard.service;

import com.example.advancedBoard.dto.request.PostCreateRequest;
import com.example.advancedBoard.dto.request.PostUpdateRequest;
import com.example.advancedBoard.dto.response.CommentResponse;
import com.example.advancedBoard.dto.response.PostDetailResponse;
import com.example.advancedBoard.dto.response.PostListResponse;
import com.example.advancedBoard.entity.Board;
import com.example.advancedBoard.entity.Post;
import com.example.advancedBoard.entity.User;
import com.example.advancedBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final BoardService boardService;

    /**
     * 게시글 생성
     */
    public PostDetailResponse createPost(PostCreateRequest request) {
        User user = userService.findUserEntityById(request.getUserId());
        Board board = boardService.findBoardEntityById(request.getBoardId());

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .board(board)
                .build();

        Post savedPost = postRepository.save(post);
        return convertToDetailResponse(savedPost);
    }

    /**
     * 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long id) {
        Post post = findPostEntityById(id);
        return convertToDetailResponse(post);
    }

    /**
     * 전체 게시글 목록 조회
     */
    @Transactional(readOnly = true)
    public List<PostListResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시판의 게시글 목록 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public List<PostListResponse> getPostsByBoard(Long boardId) {
        Board board = boardService.findBoardEntityById(boardId);
        return postRepository.findByBoardOrderByCreatedAtDesc(board).stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 게시글 목록 조회
     */
    @Transactional(readOnly = true)
    public List<PostListResponse> getPostsByUser(Long userId) {
        User user = userService.findUserEntityById(userId);
        return postRepository.findByUser(user).stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 제목으로 검색
     */
    @Transactional(readOnly = true)
    public List<PostListResponse> searchByTitle(String keyword) {
        validateKeyword(keyword);
        return postRepository.findByTitleContaining(keyword).stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 내용으로 검색
     */
    @Transactional(readOnly = true)
    public List<PostListResponse> searchByContent(String keyword) {
        validateKeyword(keyword);
        return postRepository.findByContentContaining(keyword).stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 제목 또는 내용으로 검색
     */
    @Transactional(readOnly = true)
    public List<PostListResponse> searchPosts(String keyword) {
        validateKeyword(keyword);
        return postRepository.searchByTitleOrContent(keyword).stream()
                .map(this::convertToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 수정
     */
    public PostDetailResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = findPostEntityById(id);

        // 제목 수정
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            post.updateTitle(request.getTitle());
        }

        // 내용 수정
        if (request.getContent() != null && !request.getContent().isEmpty()) {
            post.updateContent(request.getContent());
        }

        Post savedPost = postRepository.save(post);
        return convertToDetailResponse(savedPost);
    }

    /**
     * 게시글 삭제
     */
    public void deletePost(Long id) {
        Post post = findPostEntityById(id);
        postRepository.delete(post);
    }

    // ========== Private 헬퍼 메서드 ==========

    /**
     * 검색어 유효성 검증
     */
    private void validateKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }
    }

    /**
     * Entity -> ListResponse 변환
     */
    private PostListResponse convertToListResponse(Post post) {
        int commentCount = (int) commentService.countByPost(post);

        return PostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .username(post.getUser().getUsername())
                .boardName(post.getBoard().getName())
                .commentCount(commentCount)
                .createdAt(post.getCreatedAt())
                .build();
    }

    /**
     * Entity -> DetailResponse 변환
     */
    private PostDetailResponse convertToDetailResponse(Post post) {
        int commentCount = (int) commentService.countByPost(post);

        // 댓글 목록 조회
        List<CommentResponse> comments = commentService.findCommentEntitiesByPost(post).stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .userId(comment.getUser().getId())
                        .username(comment.getUser().getUsername())
                        .postId(comment.getPost().getId())
                        .postTitle(comment.getPost().getTitle())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .boardId(post.getBoard().getId())
                .boardName(post.getBoard().getName())
                .commentCount(commentCount)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(comments)
                .build();
    }

    // ========== Public 헬퍼 메서드 (다른 Service에서 사용) ==========

    /**
     * 특정 게시판의 게시글 수 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public long countByBoard(Board board) {
        return postRepository.countByBoard(board);
    }

    /**
     * Post Entity 조회 (다른 Service에서 사용)
     */
    @Transactional(readOnly = true)
    public Post findPostEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + id));
    }
}
