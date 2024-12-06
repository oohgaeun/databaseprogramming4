package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Chat;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.service.ChatService;
import com.dbp.tpj.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    public String viewPost(@PathVariable Long id, Model model) {
        //Service를 통해 게시물 데이터를 가져옴
        Post post = postService.getPostById(id);
        List<Chat> chats = chatService.getChatsByPost(post); // 댓글 가져오기

        //가져온 데이터를 모델에 추가하여 뷰에 전달
        model.addAttribute("post", post);
        model.addAttribute("chats", chats);

        //postdetail.html로 이동
        return "posts/postdetail";
    }
}
