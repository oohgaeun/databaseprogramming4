package com.dbp.tpj.service;

import com.dbp.tpj.domain.Chat;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getChatsByPost(Post post) {
        return chatRepository.findByPost(post);
    }
}
