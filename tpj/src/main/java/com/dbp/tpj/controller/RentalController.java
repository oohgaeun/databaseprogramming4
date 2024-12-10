package com.dbp.tpj.controller;

import com.dbp.tpj.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // 대여 승인 처리
    @PostMapping("/{postId}/approve")
    public String approveRental(@PathVariable Long postId) {
        rentalService.approveRental(postId);
        return "redirect:/posts/" + postId; // 게시글 상세 페이지로 리다이렉트
    }

    // 반납 처리
    @PostMapping("/{rentalId}/return")
    public String returnRental(
            @PathVariable String rentalId,
            @RequestParam String lenderId,
            @RequestParam String borrowerId) {
        rentalService.returnRental(rentalId, lenderId, borrowerId);
        return "redirect:/items/records"; // 물품 기록 페이지로 리다이렉트
    }
}