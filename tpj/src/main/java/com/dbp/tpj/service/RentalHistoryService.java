package com.dbp.tpj.service;

import com.dbp.tpj.domain.RentalHistory;
import com.dbp.tpj.repository.PROCEDURERepository;
import com.dbp.tpj.repository.RentalHistoryRepository;
import com.dbp.tpj.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RentalHistoryService {

    private final PROCEDURERepository procedureRepository;
    private final RentalHistoryRepository rentalHistoryRepository;
    private RentalRepository rentalRepository;

    @Autowired
    public RentalHistoryService(PROCEDURERepository procedureRepository,
                                RentalHistoryRepository rentalHistoryRepository,
                                RentalRepository rentalRepository) {
        this.procedureRepository = procedureRepository;
        this.rentalHistoryRepository = rentalHistoryRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<RentalHistory> getLentRecords(String userId) {
        return rentalHistoryRepository.findLentRecordsWithItemName(userId);
    }

    public List<RentalHistory> getBorrowedRecords(String userId) {
        return rentalHistoryRepository.findBorrowedRecordsWithItemName(userId);
    }

    public List<RentalHistory> getBorrowedRecordsWithPostId(String userId) {
        List<RentalHistory> borrowedRecords = rentalHistoryRepository.findBorrowedRecordsWithItemName(userId);

        borrowedRecords.forEach(record -> {
            String itemId = record.getItem().getItemId();
            Long postId = rentalRepository.findPostIdByItemId(itemId); // RentalRepository에서 PostID 조회
            record.setPostId(postId); // RentalHistory에 PostID 설정
        });

        return borrowedRecords;
    }

    /**
     * 반납 처리 로직
     * @param postId 게시글 ID
     */
    public void processReturn(Long postId) {
        try {
            // Rental_Return_Date 프로시저를 '반납' 시그널로 호출
            procedureRepository.executeRentalProcedure(postId, "반납");
        } catch (Exception e) {
            throw new RuntimeException("반납 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    /**
     * 연체자 신용 점수 감소
     */
    @Transactional
    public void executeDecreaseCreditOverdue() {
        try {
            // Decrease_Credit_Overdue 프로시저 호출
            procedureRepository.executeDecreaseCreditOverdue();
        } catch (Exception e) {
            throw new RuntimeException("연체자 신용 감소 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
}