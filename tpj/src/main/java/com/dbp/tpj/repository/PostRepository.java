package com.dbp.tpj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 제목 또는 내용 검색
    @Query("SELECT p FROM Post p WHERE (:search IS NULL OR p.title LIKE %:search% OR p.postContents LIKE %:search%)")
    Page<Post> searchByTitleOrContent(@Param("search") String search, Pageable pageable);

    // 제목 또는 내용 + 대여 상태 검색
    @Query("SELECT p FROM Post p WHERE (:search IS NULL OR p.title LIKE %:search% OR p.postContents LIKE %:search%) AND p.rentalState = :rentalState")
    Page<Post> searchByTitleOrContentAndRentalState(@Param("search") String search, @Param("rentalState") String rentalState, Pageable pageable);

    @Query("SELECT COALESCE(MAX(p.postId), 0) + 1 FROM Post p")
    Long getNextPostId();

}
