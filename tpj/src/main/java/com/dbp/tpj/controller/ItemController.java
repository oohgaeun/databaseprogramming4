package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Student;
import com.dbp.tpj.domain.Item;
import com.dbp.tpj.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 물품 목록 조회
    @GetMapping("/list")
    public String getItemList(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loggedInUser");
        List<Map<String, Object>> groupedItems = itemService.getGroupedItemsByUserId(userId);
        model.addAttribute("groupedItems", groupedItems);
        return "items/itemlist"; // itemlist.html 렌더링
    }

    // 물품 등록 페이지 이동
    @GetMapping("/register")
    public String showRegisterPage() {
        return "items/itemregister"; // itemregister.html 렌더링
    }

    // 물품 등록 처리
    @PostMapping("/register")
    public String registerItem(@ModelAttribute Item item, @RequestParam("quantity") int quantity, HttpSession session) {
        String userId = (String) session.getAttribute("loggedInUser"); // 세션에서 사용자 ID 가져오기
        item.setStudent(new Student(userId)); // 사용자 ID를 기반으로 등록자 설정

        // 물품 등록 서비스 호출
        itemService.registerItem(item, quantity);

        return "redirect:/items/list"; // 등록 후 물품 목록으로 리다이렉트
    }

    @PostMapping("/update")
    public String updateItemQuantity(@RequestParam String itemName, @RequestParam String category,
                                     @RequestParam int quantity, HttpSession session, RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("loggedInUser"); // 세션에서 사용자 ID 가져오기

        boolean success = itemService.updateItemQuantity(userId, itemName, category, quantity);

        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "대여중인 아이템이 있어 수량을 줄일 수 없습니다.");
        }

        return "redirect:/items/list";
    }

    // 물품 삭제
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/items/list"; // 삭제 후 물품 목록으로 리다이렉트
    }
}