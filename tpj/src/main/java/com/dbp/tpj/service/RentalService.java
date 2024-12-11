package com.dbp.tpj.service;

import com.dbp.tpj.domain.Item;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Rental;
import com.dbp.tpj.repository.PROCEDURERepository;
import com.dbp.tpj.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RentalService {
    private final RentalRepository rentalRepository;
    private final PROCEDURERepository procedureRepository;

    @Autowired// 하나의 생성자로 통합
    public RentalService(RentalRepository rentalRepository, PROCEDURERepository procedureRepository) {
        this.rentalRepository = rentalRepository;
        this.procedureRepository = procedureRepository;
    }

    public void linkItemsToPost(List<Item> items, Post post) {
        for (Item item : items) {
            // 이미 Rental 테이블에 존재하는 상품인지 확인
            if (rentalRepository.existsByItemAndPost(item, post)) {
                throw new IllegalStateException("이미 등록된 상품입니다: " + item.getItemName());
            }
            Rental rental = new Rental(item, post);
            rentalRepository.save(rental);
        }
    }

    public void approveRental(Long postId) {
        System.out.println("Post ID: " + postId);
        System.out.println("Signal: 대여");
        // 대여 승인 처리
        procedureRepository.executeRentalProcedure(postId, "대여");
    }

    public void returnRental(String itemId, String lenderId, String borrowerId) {
        // 반납 처리
        procedureRepository.executeRentalStatistics(itemId, lenderId, borrowerId, "반납");
    }

}
