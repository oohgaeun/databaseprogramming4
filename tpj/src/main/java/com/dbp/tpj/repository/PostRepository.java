package com.dbp.tpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
