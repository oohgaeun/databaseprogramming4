package com.dbp.tpj.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostID")
    private Long postId; // 게시물 ID

    @Column(name = "Rentalstate", nullable = false)
    private String rentalState; // 거래 상태 ('대여가능', '대여중', '대여대기')

    @Column(name = "Title", nullable = false)
    private String title; // 제목

    @Column(name = "Post_contents", nullable = false, columnDefinition = "CLOB")
    private String postContents; // 게시물 내용

    @Column(name = "Interestcount", columnDefinition = "NUMBER DEFAULT 0")
    private Integer interestCount; // 관심 수

    @Column(name = "Creationday", nullable = false)
    private LocalDateTime creationDay; // 작성 일자

    @ManyToOne
    @JoinColumn(name = "ItemID", nullable = false) // 대여 대상 물품
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ID", nullable = false) // 작성자
    private Student student;

    // 기본 생성자
    public Post() {
    }

    // 모든 필드를 포함한 생성자
    public Post(Long postId, String rentalState, String title, String postContents, Integer interestCount, LocalDateTime creationDay, Item item, Student student) {
        this.postId = postId;
        this.rentalState = rentalState;
        this.title = title;
        this.postContents = postContents;
        this.interestCount = interestCount;
        this.creationDay = creationDay;
        this.item = item;
        this.student = student;
    }

    // Getter와 Setter
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getRentalState() {
        return rentalState;
    }

    public void setRentalState(String rentalState) {
        this.rentalState = rentalState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostContents() {
        return postContents;
    }

    public void setPostContents(String postContents) {
        this.postContents = postContents;
    }

    public Integer getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(Integer interestCount) {
        this.interestCount = interestCount;
    }

    public LocalDateTime getCreationDay() {
        return creationDay;
    }

    public void setCreationDay(LocalDateTime creationDay) {
        this.creationDay = creationDay;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}