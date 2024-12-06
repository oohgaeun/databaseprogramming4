package com.dbp.tpj.service;

import com.dbp.tpj.domain.RentalHistory;
import com.dbp.tpj.repository.RentalHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalHistoryService {

    private final RentalHistoryRepository rentalHistoryRepository;

    public RentalHistoryService(RentalHistoryRepository rentalHistoryRepository) {
        this.rentalHistoryRepository = rentalHistoryRepository;
    }

    // 대여 기록 조회
    public List<RentalHistory> getLentRecords(String lenderId) {
        return rentalHistoryRepository.findByLender_Id(lenderId);
    }

    // 대여 받은 기록 조회
    public List<RentalHistory> getBorrowedRecords(String borrowerId) {
        return rentalHistoryRepository.findByBorrower_Id(borrowerId);
    }
}