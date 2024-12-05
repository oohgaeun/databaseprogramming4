package com.dbp.tpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsById(String id); // 학번 중복 확인
}
