package com.example.advancedBoard.repository;

import com.example.advancedBoard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 게시판명으로 조회
    Optional<Board> findByName(String name);

    // 게시판명 존재 여부 확인
    boolean existsByName(String name);
}
