package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Post;
import com.dbp.tpj.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        // Service 계층을 통해 게시물 데이터 호출
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "postslist"; // templates/postslist.html 반환
    }

    @GetMapping("/posts/add")
    public String AddPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "postadd"; // templates/postadd.html
    }

    @PostMapping("/posts/add")
    public String addPost(@ModelAttribute Post post) {
        postService.addPost(post);
        return "redirect:/posts";
    }
}
