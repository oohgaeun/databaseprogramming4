package com.dbp.tpj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "ItemID") // Primary Key
    private Long itemId; // 물품 ID (String -> Long 변경, 아래 동일(테이블에 number로 정의되어 있음)

    @Column(name = "Itemname", nullable = false)
    private String itemName; // 물품명

    @Column(name = "Category")
    private String category; // 카테고리

    @Column(name = "Rentalstate", nullable = false)
    private String rentalState; // 대여 상태 ('대여가능', '대여중')

    @Column(name = "Returnday")
    private LocalDateTime returnDay; // 반납 기한

    @ManyToOne
    @JoinColumn(name = "ID", nullable = false) // 등록자
    private Student student;

    // 기본 생성자
    public Item() {
    }

    // 모든 필드를 포함한 생성자
    public Item(Long itemId, String itemName, String category, String rentalState, LocalDateTime returnDay, Student student) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.rentalState = rentalState;
        this.returnDay = returnDay;
        this.student = student;
    }

    // Getter와 Setter
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRentalState() {
        return rentalState;
    }

    public void setRentalState(String rentalState) {
        this.rentalState = rentalState;
    }

    public LocalDateTime getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(LocalDateTime returnDay) {
        this.returnDay = returnDay;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
