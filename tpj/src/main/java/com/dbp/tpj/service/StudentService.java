package com.dbp.tpj.service;

import org.springframework.stereotype.Service;
import com.dbp.tpj.domain.Student;
import com.dbp.tpj.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void registerStudent(Student student) {
        if (studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다.");
        }

        student.setCredit(10);
        student.setRtCount(0);

        studentRepository.save(student);
    }

    public boolean authenticate(String id, String password) {
        return studentRepository.findById(id)
                .map(student -> student.getPassword().equals(password))
                .orElse(false);
    }

    public String findStudentNameById(String id) {
        return studentRepository.findById(id)
                .map(Student::getName) // Student 객체의 이름 가져오기
                .orElse("Unknown User");
    }
}
