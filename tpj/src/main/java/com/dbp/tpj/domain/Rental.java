package com.dbp.tpj.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(RentalId.class) // 복합키 클래스 연결
@Table(name = "rental")
public class Rental {

    @Id
    @Column(name = "itemID")
    private String itemId; // 복합키의 일부

    @Id
    @Column(name = "postID")
    private Long postId; // 복합키의 일부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemID", referencedColumnName = "itemID", insertable = false, updatable = false)
    private Item item; // 물품 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postID", referencedColumnName = "postID", insertable = false, updatable = false)
    private Post post; // 게시물 정보

    // 기본 생성자
    public Rental() {}

    // 생성자
    public Rental(String itemId, Long postId) {
        this.itemId = itemId;
        this.postId = postId;
    }

    // Getter 및 Setter
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
