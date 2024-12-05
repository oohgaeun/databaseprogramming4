package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Student;
import com.dbp.tpj.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 회원가입 폼 표시
    @GetMapping("/students/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/register";
    }

    // 회원가입 처리
    @PostMapping("/students/register")
    public String registerStudent(@ModelAttribute Student student) {
        studentService.registerStudent(student);
        return "redirect:/"; // 회원가입 후 메인 페이지로 리다이렉트
    }
}
