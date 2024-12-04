package com.dbp.tpj.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChatID")
    private Long chatId; // 채팅 ID

    @ManyToOne
    @JoinColumn(name = "PostID", nullable = false) // 관련 게시물
    private Post post;

    @ManyToOne
    @JoinColumn(name = "ID", nullable = false) // 작성자
    private Student student;

    @Column(name = "Chat_contents", nullable = false, columnDefinition = "CLOB")
    private String chatContents; // 채팅 내용

    @Column(name = "Permission_state")
    private String permissionState; // 승인 상태 ('대기', '승낙')

    // 기본 생성자
    public Chat() {
    }

    // 모든 필드를 포함한 생성자
    public Chat(Long chatId, Post post, Student student, String chatContents, String permissionState) {
        this.chatId = chatId;
        this.post = post;
        this.student = student;
        this.chatContents = chatContents;
        this.permissionState = permissionState;
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

    public String getChatContents() {
        return chatContents;
    }

    public void setChatContents(String chatContents) {
        this.chatContents = chatContents;
    }

    public String getPermissionState() {
        return permissionState;
    }

    public void setPermissionState(String permissionState) {
        this.permissionState = permissionState;
    }

}
