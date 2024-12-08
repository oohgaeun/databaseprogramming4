package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Student;
import com.dbp.tpj.domain.Item;
import com.dbp.tpj.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


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
    //물품 수정/삭제
    @PostMapping(value = "/applyChanges", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public  Map<String, Object> applyChanges(@RequestBody Map<String, Object> requestData, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("loggedInUser");

        // 응답 데이터를 담을 Map 생성
        Map<String, Object> response = new HashMap<>();

        if (userId == null) {
            response.put("success", false);
            response.put("errorMessage", "로그인이 필요합니다.");
            return response; // JSON 형태로 반환
        }

        // JSON 데이터 파싱
        List<Map<String, Object>> updates = (List<Map<String, Object>>) requestData.get("updates");
        updates.forEach(update -> {
            System.out.println("Update Entry: " + update); // 디버깅 출력
        });
        List<String> deletions = (List<String>) requestData.get("deletions");
        //디버그
        System.out.println("Request Data: " + requestData);
        System.out.println("Updates: " + updates);
        System.out.println("Deletions: " + deletions);

        // 서비스 호출
        List<String> errors = itemService.updateAndDeleteItems(userId, updates, deletions);

        if (!errors.isEmpty()) {
            response.put("success", false);
            response.put("errorMessage", String.join(", ", errors));
        } else {
            response.put("success", true);
            response.put("message", "수정 사항이 성공적으로 적용되었습니다.");
            response.put("redirectUrl", "/items/list"); // 리다이렉트 URL 포함
        }

        return response; // Map을 JSON으로 변환하여 반환
    }

    // 물품 삭제
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/items/list"; // 삭제 후 물품 목록으로 리다이렉트
    }
}