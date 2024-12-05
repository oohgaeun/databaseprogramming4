package com.dbp.tpj.service;

import com.dbp.tpj.domain.Post;
import com.dbp.tpj.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Post> getPosts(Pageable pageable) {
        // Repository의 페이징 메서드를 호출
        return postRepository.findAll(pageable);
    }
    // 특정 게시물 조회
    public Post getPostById(Long id) {
        // Repository를 통해 데이터베이스에서 게시물 조회
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));
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
