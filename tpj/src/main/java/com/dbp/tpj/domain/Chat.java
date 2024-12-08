package com.dbp.tpj.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "chats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "student_id"})
})
public class Chat {

    @Id
    @Column(name = "chat_id")
    private Long chatId; // 댓글 ID (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "PostID", nullable = false)
    private Post post; // 게시물 (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "ID", nullable = false)
    private Student student; // 작성자 (FK)

    @Column(name = "content", nullable = false, columnDefinition = "CLOB")
    private String content; // 댓글 내용

    @Column(name = "status", nullable = true)
    private String status; // 상태 ('대기', '승낙')

    // 기본 생성자
    public Chat() {}

    // 모든 필드 포함 생성자
    public Chat(Long chatId, Post post, Student student, String content, String status) {
        this.chatId = chatId;
        this.post = post;
        this.student = student;
        this.content = content;
        this.status = status;
    }

    // Getter와 Setter
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
