package com.dbp.tpj.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rental")
public class Rental {

    @EmbeddedId
    private RentalId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId") // 복합키의 itemId를 참조
    @JoinColumn(name = "itemID", referencedColumnName = "itemID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId") // 복합키의 postId를 참조
    @JoinColumn(name = "postID", referencedColumnName = "postID")
    private Post post;

    // 기본 생성자
    public Rental() {
    }

    // 생성자
    public Rental(Item item, Post post) {
        this.id = new RentalId(item.getItemId(), post.getPostId());
        this.item = item;
        this.post = post;
    }

    // Getter 및 Setter
    public RentalId getId() {
        return id;
    }

    public void setId(RentalId id) {
        this.id = id;
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
