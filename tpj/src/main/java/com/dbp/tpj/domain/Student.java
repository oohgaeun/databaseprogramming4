package com.dbp.tpj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PW", nullable = false)
    private String password;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Phonenum", nullable = false)
    @Pattern(regexp = "010-\\d{4}-\\d{4}", message = "전화번호는 010-1234-5678 형식이어야 합니다.")
    private String phoneNumber;

    @Column(name = "Credit", columnDefinition = "NUMBER DEFAULT 100")
    private Integer credit;

    @Column(name = "RTcount", columnDefinition = "NUMBER DEFAULT 0")
    private Integer rtCount;


    // 기본 생성자
    public Student() {
    }

    // 모든 필드를 포함한 생성자
    public Student(String id, String password, String name, String phoneNumber, Integer credit, Integer rtCount) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.credit = credit;
        this.rtCount = rtCount;
    }

    // Getter와 Setter 메서드
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getRtCount() {
        return rtCount;
    }

    public void setRtCount(Integer rtCount) {
        this.rtCount = rtCount;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", credit=" + credit +
                ", rtCount=" + rtCount +
                '}';
    }
}
