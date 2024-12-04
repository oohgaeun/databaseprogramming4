package com.dbp.tpj.service;

import com.dbp.tpj.domain.Post;
import com.dbp.tpj.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 모든 게시물 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 게시물 조회
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    // 게시물 추가
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    // 게시물 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
