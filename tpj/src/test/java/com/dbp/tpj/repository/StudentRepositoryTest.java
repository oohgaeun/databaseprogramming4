package com.dbp.tpj.repository;

import com.dbp.tpj.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveAndDeleteStudent() {
        // 1. 데이터 삽입
        Student student = new Student("20230001", "password123", "John Doe", "010-1234-5678", 100, 0);
        studentRepository.save(student);

        // 2. 데이터 조회
        List<Student> students = studentRepository.findAll();
        System.out.println("학생 데이터: " + students);
        assertThat(students).isNotEmpty();

        Optional<Student> foundStudent = studentRepository.findById("20230001");
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getName()).isEqualTo("John Doe");

//        // 3. 데이터 삭제
//        studentRepository.deleteById("20230001");
//        assertThat(studentRepository.findById("20230001")).isEmpty();
    }
}

