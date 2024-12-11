package com.dbp.tpj.controller;

import com.dbp.tpj.domain.RentalHistory;
import com.dbp.tpj.repository.RentalRepository;
import com.dbp.tpj.service.RentalHistoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal; // Spring Security 사용 시 필요
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemRecordsController {

    private final RentalHistoryService rentalHistoryService;
    private final RentalRepository rentalRepository;

    @Autowired
    public ItemRecordsController(RentalHistoryService rentalHistoryService, RentalRepository rentalRepository) {
        this.rentalHistoryService = rentalHistoryService;
        this.rentalRepository = rentalRepository;
    }

    @GetMapping("/records")
    public String getItemRecords(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loggedInUser");
        if (userId == null) {
            return "redirect:/login";
        }

        // 대여 기록과 대여받은 기록 조회
        List<RentalHistory> lentRecords = rentalHistoryService.getLentRecords(userId);
        List<RentalHistory> borrowedRecords = rentalHistoryService.getBorrowedRecordsWithPostId(userId); // PostID 포함된 데이터 호출

        model.addAttribute("lentRecords", lentRecords);
        model.addAttribute("borrowedRecords", borrowedRecords);

        return "items/itemrecords";
    }
}