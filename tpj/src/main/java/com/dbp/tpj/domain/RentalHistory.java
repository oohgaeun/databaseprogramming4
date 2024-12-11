package com.dbp.tpj.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentalhistory") // <- H를 h로 바꾸어야 한다
public class RentalHistory {

    @Id
    @Column(name = "HistoryID", nullable = false)
    private String historyId; // 이력 ID

    @Column(name = "Rentstart", nullable = false)
    private LocalDate rentStart; // 대여 시작일

    @Column(name = "Rentend")
    private LocalDate rentEnd; // 대여 종료일 (반납 안 된 경우 NULL)

    @ManyToOne
    @JoinColumn(name = "LenderID", referencedColumnName = "StudentID")
    private Student lender;

    @ManyToOne
    @JoinColumn(name = "BorrowerID", referencedColumnName = "StudentID")
    private Student borrower;

    @ManyToOne
    @JoinColumn(name = "ItemID", referencedColumnName = "ItemID")
    private Item item; // 대여된 물품

    @Column(name = "returnstatus", nullable = false)
    private String returnStatus; // 반납 상태 ('기한준수', '기한초과', '미반납')

    @Transient // 테이블에 영향을 미치지 않음
    private Long postId;



    // 기본 생성자
    public RentalHistory() {
    }

    // 모든 필드를 포함한 생성자
    public RentalHistory(String historyId, LocalDate rentStart, LocalDate rentEnd, String returnStatus,
                         Student lender, Student borrower, Item item) {
        this.historyId = historyId;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.returnStatus = returnStatus;
        this.lender = lender;
        this.borrower = borrower;
        this.item = item;
    }

    // Getter와 Setter
    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public LocalDate getRentStart() {
        return rentStart;
    }

    public void setRentStart(LocalDate rentStart) {
        this.rentStart = rentStart;
    }

    public LocalDate getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(LocalDate rentEnd) {
        this.rentEnd = rentEnd;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Student getLender() {
        return lender;
    }

    public void setLender(Student lender) {
        this.lender = lender;
    }

    public Student getBorrower() {
        return borrower;
    }

    public void setBorrower(Student borrower) {
        this.borrower = borrower;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}