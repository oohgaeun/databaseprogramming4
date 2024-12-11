package com.dbp.tpj.repository;

import com.dbp.tpj.domain.Item;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Rental;
import com.dbp.tpj.domain.RentalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    //대여가능한 물품 찾기
    @Query("SELECT i FROM Item i WHERE i.student.id = :lenderId AND i.rentalState = '대여가능' AND i.itemId NOT IN (" +
            "SELECT r.item.itemId FROM Rental r WHERE r.post.postId = :postId)")
    List<Item> findAvailableItemsForPost(@Param("lenderId") String lenderId, @Param("postId") Long postId);


    //대여중으로 업데이트
    @Modifying
    @Query(value = "UPDATE items SET rental_state = '대여중', return_day = SYSDATE + 7 WHERE item_id = :itemId", nativeQuery = true)
    void markItemAsRented(@Param("itemId") String itemId);

    @Query("SELECT r.post.postId FROM Rental r WHERE r.item.itemId = :itemId")
    Long findPostIdByItemId(@Param("itemId") String itemId);

}
