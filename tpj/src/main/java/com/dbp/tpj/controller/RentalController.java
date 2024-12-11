package com.dbp.tpj.controller;

import com.dbp.tpj.service.RentalHistoryService;
import com.dbp.tpj.service.RentalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final RentalHistoryService rentalHistoryService;

    @Autowired
    public RentalController(RentalService rentalService, RentalHistoryService rentalHistoryService) {
        this.rentalService = rentalService;
        this.rentalHistoryService = rentalHistoryService;
    }

    // 대여 승인 처리
    @PostMapping("/{postId}/approve")
    public String approveRental(@PathVariable Long postId) {
        rentalService.approveRental(postId);
        return "redirect:/posts/" + postId; // 게시글 상세 페이지로 리다이렉트
    }

    // 반납 처리
    @PostMapping("/{postId}/return")
    public ResponseEntity<String> processReturn(@RequestParam Long postId, HttpSession session) {
        // 사용자 확인
        String userId = (String) session.getAttribute("loggedInUser");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            // 서비스 호출: 반납 처리 (postId만 전달)
            rentalHistoryService.processReturn(postId);
            return ResponseEntity.ok("반납 처리가 완료되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("반납 처리 중 문제가 발생했습니다.");
        }
    }
}