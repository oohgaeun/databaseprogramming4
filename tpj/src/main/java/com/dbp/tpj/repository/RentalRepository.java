package com.dbp.tpj.repository;

import com.dbp.tpj.domain.Item;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Rental;
import com.dbp.tpj.domain.RentalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, RentalId> {
    @Query("SELECT r.item FROM Rental r WHERE r.post.postId = :postId")
    List<Item> findItemsByPostId(Long postId);

    @Query("SELECT r.item.itemId FROM Rental r WHERE r.item.student.id = :studentId")
    List<String> findRentedItemIdsByStudentId(@Param("studentId") String studentId);


    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Rental r WHERE r.item = :item AND r.post = :post")
    boolean existsByItemAndPost(@Param("item") Item item, @Param("post") Post post);
}
