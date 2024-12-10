package com.dbp.tpj.service;

import com.dbp.tpj.domain.Item;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Rental;
import com.dbp.tpj.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Transactional
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
}
