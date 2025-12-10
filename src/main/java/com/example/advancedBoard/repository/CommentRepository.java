package com.example.advancedBoard.repository;

import com.example.advancedBoard.entity.Comment;
import com.example.advancedBoard.entity.Post;
import com.example.advancedBoard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글 조회
    List<Comment> findByPost(Post post);

    // 특정 게시글의 댓글을 작성일순으로 조회
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);

    // 특정 사용자가 작성한 댓글 조회
    List<Comment> findByUser(User user);

    // 특정 게시글의 댓글 수 조회
    long countByPost(Post post);

    // 특정 사용자의 댓글 수 조회
    long countByUser(User user);
}
