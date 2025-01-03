package com.dbp.tpj.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 특정 사용자가 등록한 물품 모두 조회
    @Query("SELECT i FROM Item i WHERE i.student.id = :userId")
    List<Item> findByUserId(String userId);

    // 특정 사용자가 등록한 물품 물품명 중복 회피해서 조회
    @Query("SELECT i.itemName, i.category, COUNT(i) FROM Item i WHERE i.student.id = :userId GROUP BY i.itemName, i.category")
    List<Object[]> findGroupedItemsByUserId(@Param("userId") String userId);

    // 특정 사용자와 물품명을 기준으로 물품 개수 조회
    @Query("SELECT i.itemName, COUNT(i) FROM Item i WHERE i.student.id = :userId GROUP BY i.itemName")
    List<Object[]> findItemCountsByUserId(@Param("userId") String userId);


    // 특정 사용자의 물품 중 이름으로 검색 (옵션)
    @Query("SELECT i FROM Item i WHERE i.student.id = :userId AND i.itemName = :itemName AND i.category = :category")
    List<Item> findByUserIdAndItemNameAndCategory(@Param("userId") String userId,
                                                  @Param("itemName") String itemName,
                                                  @Param("category") String category);

    @Query("SELECT i FROM Item i WHERE i.itemName = :itemName AND i.student.id = :userId")
    List<Item> findByItemNameAndUserId(@Param("itemName") String itemName, @Param("userId") String userId);



}
