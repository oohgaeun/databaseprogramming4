package com.dbp.tpj.repository;

import com.dbp.tpj.domain.Item;
import com.dbp.tpj.domain.Rental;
import com.dbp.tpj.domain.RentalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, RentalId> {
    @Query("SELECT r.item FROM Rental r WHERE r.post.postId = :postId")
    List<Item> findItemsByPostId(Long postId);
}
