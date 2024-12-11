package com.dbp.tpj.repository;

import com.dbp.tpj.domain.RentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHistoryRepository extends JpaRepository<RentalHistory, String> {

    // 대여해 준 기록 조회 (itemName 포함)
    @Query("SELECT rh FROM RentalHistory rh JOIN FETCH rh.item i WHERE rh.lender.id = :lenderId")
    List<RentalHistory> findLentRecordsWithItemName(@Param("lenderId") String lenderId);

    // 대여 받은 기록 조회 (itemName 포함)
    @Query("SELECT rh FROM RentalHistory rh JOIN FETCH rh.item i WHERE rh.borrower.id = :borrowerId")
    List<RentalHistory> findBorrowedRecordsWithItemName(@Param("borrowerId") String borrowerId);

    // 대여 상태(ReturnStatus)로 기록 조회
    List<RentalHistory> findByReturnStatus(String returnStatus);

    // 특정 물품(ItemID)으로 기록 조회
    List<RentalHistory> findByItem_ItemId(String  itemId);

}