package com.dbp.tpj.repository;

import com.dbp.tpj.domain.RentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHistoryRepository extends JpaRepository<RentalHistory, String> {

    // 대여자(LenderID)로 대여 기록 조회
    List<RentalHistory> findByLender_Id(String lenderId);

    // 대여받은 자(BorrowerID)로 대여 기록 조회
    List<RentalHistory> findByBorrower_Id(String borrowerId);

    // 대여 상태(ReturnStatus)로 기록 조회
    List<RentalHistory> findByReturnStatus(String returnStatus);

    // 특정 물품(ItemID)으로 기록 조회
    List<RentalHistory> findByItem_ItemId(String itemId);
}