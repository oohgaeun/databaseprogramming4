package com.dbp.tpj.domain;

import java.io.Serializable;
import java.util.Objects;

public class RentalId implements Serializable {
    private String itemId;
    private Long postId;

    // Default constructor
    public RentalId() {}

    // Constructor
    public RentalId(String itemId, Long postId) {
        this.itemId = itemId;
        this.postId = postId;
    }

    // Getters and Setters
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

    // equals and hashCode (필수)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalId rentalId = (RentalId) o;
        return Objects.equals(itemId, rentalId.itemId) && Objects.equals(postId, rentalId.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, postId);
    }
}
