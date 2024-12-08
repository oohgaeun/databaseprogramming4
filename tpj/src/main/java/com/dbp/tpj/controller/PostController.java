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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String listPosts(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Pageable pageable = PageRequest.of(page, size); // 페이지 번호와 크기를 설정
        Page<Post> postPage = postService.getPosts(pageable); // 페이징된 데이터 가져오기

        model.addAttribute("posts", postPage.getContent()); // 게시물 리스트
        model.addAttribute("currentPage", postPage.getNumber()); // 현재 페이지
        model.addAttribute("totalPages", postPage.getTotalPages()); // 총 페이지 수

        return "posts/postslist";
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


}
