package com.dbp.tpj.service;

import org.springframework.stereotype.Service;
import com.dbp.tpj.domain.Student;
import com.dbp.tpj.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void registerStudent(Student student) {
        // 기본값 설정
        student.setCredit(100); // 초기 크레딧 설정
        student.setRtCount(0);  // 초기 거래 수 설정

        // 데이터베이스에 저장
        studentRepository.save(student);
    }
}
