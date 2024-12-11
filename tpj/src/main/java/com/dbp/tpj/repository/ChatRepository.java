package com.dbp.tpj.repository;


import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    // 특정 게시물의 댓글 리스트 가져오기
    List<Chat> findByPost(Post post);

    // 특정 게시물에서 특정 학생의 댓글 존재 여부 확인
    boolean existsByPostAndStudent(Post post, Student student);

    @Query("SELECT MAX(c.chatId) FROM Chat c")
    Optional<Long> findMaxChatId();

    //댓글 상태 업데이트
    @Modifying
    @Query("UPDATE Chat c SET c.status = '승낙' WHERE c.chatId = :chatId")
    void updateChatStatusToAccepted(@Param("chatId") Long chatId);
}