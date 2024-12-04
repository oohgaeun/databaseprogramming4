package com.dbp.tpj.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rentalwait")
public class RentalWait {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WaitNumber")
    private Integer waitNumber; // 대기 번호

    @ManyToOne
    @JoinColumn(name = "Itemname", nullable = false) // 물품명
    private Item item;

    @ManyToOne
    @JoinColumn(name = "WaitingID", nullable = false) // 대기자 ID
    private Student student;

    // 기본 생성자
    public RentalWait() {
    }

    // 모든 필드를 포함한 생성자
    public RentalWait(Integer waitNumber, Item item, Student student) {
        this.waitNumber = waitNumber;
        this.item = item;
        this.student = student;
    }

    // Getter와 Setter
    public Integer getWaitNumber() {
        return waitNumber;
    }

    public void setWaitNumber(Integer waitNumber) {
        this.waitNumber = waitNumber;
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
