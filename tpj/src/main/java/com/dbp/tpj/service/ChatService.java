package com.dbp.tpj.service;

import com.dbp.tpj.domain.Chat;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.domain.Student;
import com.dbp.tpj.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getChatsByPost(Post post) {
        return chatRepository.findByPost(post);
    }

    public boolean hasUserCommentedOnPost(Post post, Student student) {
        return chatRepository.existsByPostAndStudent(post, student);
    }

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

    public Long generateChatId() {
        return chatRepository.findMaxChatId().orElse(0L) + 1;
    }
}
