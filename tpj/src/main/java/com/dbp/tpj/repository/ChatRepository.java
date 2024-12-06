package com.dbp.tpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Chat;
import com.dbp.tpj.domain.Post;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByPost(Post post);
}
