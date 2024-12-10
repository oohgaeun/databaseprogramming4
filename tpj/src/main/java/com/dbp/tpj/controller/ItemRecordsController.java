package com.dbp.tpj.controller;

import com.dbp.tpj.domain.RentalHistory;
import com.dbp.tpj.service.RentalHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal; // Spring Security 사용 시 필요

@Controller
@RequestMapping("/items")
public class ItemRecordsController {

    private final RentalHistoryService rentalHistoryService;

    public ItemRecordsController(RentalHistoryService rentalHistoryService) {
        this.rentalHistoryService = rentalHistoryService;
    }

    @GetMapping("/records")
    public String getItemRecords(Model model, Principal principal) {
        //사용자 ID가 Null이면(오류이면)
        if (principal == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        String userId = principal.getName(); // 로그인된 사용자 ID 가져오기(이 부분만 정의하면 될듯

        // 대여 기록과 대여 받은 기록 조회
        model.addAttribute("lentRecords", rentalHistoryService.getLentRecords(userId));
        model.addAttribute("borrowedRecords", rentalHistoryService.getBorrowedRecords(userId));

        return "items/itemrecords"; // 렌더링할 HTML 파일 경로
    }
}