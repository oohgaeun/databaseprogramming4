package com.dbp.tpj.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<String> posts = new ArrayList<>();
        model.addAttribute("posts", posts);
        return "home";
    }
}
