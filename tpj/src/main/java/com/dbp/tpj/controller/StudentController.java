package com.dbp.tpj.controller;

import com.dbp.tpj.domain.Student;
import com.dbp.tpj.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String showSignupForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/"; // 로그인 상태에서 메인 페이지로 이동
        }

        model.addAttribute("student", new Student()); // 빈 Student 객체 전달
        return "students/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 다시 회원가입 페이지로 이동
            return "students/signup";
        }
        try {
            studentService.registerStudent(student);
            return "redirect:/login"; // 성공 시 로그인 페이지로 이동
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage()); // 중복 학번 에러 메시지 전달
            return "students/signup";
        }
    }

    // 로그인 폼
    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/"; // 로그인 상태에서 메인 페이지로 이동
        }
        return "students/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session, Model model) {
        boolean isAuthenticated = studentService.authenticate(id, password);
        if (isAuthenticated) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", id);
            String studentName = studentService.findStudentNameById(id);
            session.setAttribute("loggedInUserName", studentName); // 이름 저장
            return "redirect:/"; // 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "학번 또는 비밀번호가 잘못되었습니다.");
            return "students/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 로그인 페이지로 리다이렉트
    }
}
