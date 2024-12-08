package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Chat;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Student;
import com.dbp.tpj.service.ChatService;
import com.dbp.tpj.service.PostService;
import com.dbp.tpj.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final ChatService chatService;

    public PostController(PostService postService, ChatService chatService) {
        this.postService = postService;
        this.chatService = chatService;
    }

    @GetMapping
    public String listPosts(@RequestParam(required = false) String search,
                            @RequestParam(required = false) String rentalState,
                            @RequestParam(required = false, defaultValue = "creationDesc") String sortType,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        // '/posts'에 직접 접근한 경우 기본 필터 값으로 리다이렉트
        if (search == null && rentalState == null && page == 0 && sortType.equals("creationDesc")) {
            String encodedRentalState = URLEncoder.encode("전체", StandardCharsets.UTF_8);
            return "redirect:/posts?page=0&search=&rentalState=" + encodedRentalState + "&sortType=creationDesc";
        }

        Page<Post> postPage = postService.searchPosts(search, rentalState, sortType, page, size);

        // 검색 결과가 없을 때 처리
        if (postPage.isEmpty() && page > 0) {
            return "redirect:/posts?page=0&search=" + (search != null ? search : "") +
                    "&rentalState=" + (rentalState != null ? rentalState : "전체") +
                    "&sortType=" + sortType;
        }

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", postPage.getNumber());
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("isEmpty", postPage.isEmpty()); // 결과가 비었는지 전달
        model.addAttribute("param", new SearchParams(search, rentalState, sortType));

        return "posts/postslist";
    }

    public static class SearchParams {
        private String search;
        private String rentalState;
        private String sortType;

        public SearchParams(String search, String rentalState, String sortType) {
            this.search = search;
            this.rentalState = rentalState;
            this.sortType = sortType;
        }

        public String getSearch() { return search; }
        public String getRentalState() { return rentalState; }
        public String getSortType() { return sortType; }
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id,
                           @RequestParam(value = "errorMessage", required = false) String errorMessage,
                           Model model) {
        Post post = postService.getPostById(id);
        List<Chat> chats = chatService.getChatsByPost(post);

        model.addAttribute("post", post);
        model.addAttribute("chats", chats);

        // 오류 메시지
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "posts/postdetail";
    }


    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        String studentId = (String) session.getAttribute("loggedInUser");

        // 1. 로그인되지 않은 경우
        if (studentId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        // 2. 댓글 중복 확인
        Student student = new Student();
        student.setId(studentId);
        Post post = postService.getPostById(id);

        if (chatService.hasUserCommentedOnPost(post, student)) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 댓글을 작성하셨습니다.");
            return "redirect:/posts/" + id;
        }

        // 3. 댓글 저장
        Chat chat = new Chat(chatService.generateChatId(), post, student, content, "대기");
        chatService.saveChat(chat);

        redirectAttributes.addFlashAttribute("successMessage", "댓글이 성공적으로 작성되었습니다!");

        return "redirect:/posts/" + id;
    }

    @PostMapping("/{postId}/comments/{chatId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long chatId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        String studentId = (String) session.getAttribute("loggedInUser");

        if (studentId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        Chat chat = chatService.getChatById(chatId);

        if (!chat.getStudent().getId().equals(studentId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "본인이 작성한 댓글만 삭제할 수 있습니다.");
            return "redirect:/posts/" + postId;
        }

        chatService.deleteChat(chatId);

        redirectAttributes.addFlashAttribute("successMessage", "댓글이 삭제되었습니다.");
        return "redirect:/posts/" + postId;
    }

}
