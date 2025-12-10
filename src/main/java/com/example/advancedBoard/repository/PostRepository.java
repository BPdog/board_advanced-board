package com.example.advancedBoard.repository;

import com.example.advancedBoard.entity.Post;
import com.example.advancedBoard.entity.User;
import com.example.advancedBoard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 특정 사용자의 게시글 조회
    List<Post> findByUser(User user);

    // 특정 게시판의 게시글 조회
    List<Post> findByBoard(Board board);

    // 특정 게시판의 게시글 수 조회
    long countByBoard(Board board);  // ← 이 메서드 추가

    // 제목으로 검색
    List<Post> findByTitleContaining(String keyword);

    // 내용으로 검색
    List<Post> findByContentContaining(String keyword);

    // 제목 또는 내용으로 검색
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Post> searchByTitleOrContent(@Param("keyword") String keyword);

    // 특정 게시판의 게시글을 최신순으로 조회
    List<Post> findByBoardOrderByCreatedAtDesc(Board board);

    // 특정 사용자의 게시글 수 조회
    long countByUser(User user);
}
