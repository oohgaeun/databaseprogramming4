package com.dbp.tpj.service;

import com.dbp.tpj.domain.Item;
import com.dbp.tpj.domain.Post;
import com.dbp.tpj.repository.PostRepository;
import com.dbp.tpj.repository.RentalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final RentalRepository rentalRepository;

    public PostService(PostRepository postRepository, RentalRepository rentalRepository) {
        this.postRepository = postRepository;
        this.rentalRepository = rentalRepository;
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

    // 검색 및 필터링된 게시물 가져오기
    public Page<Post> searchPosts(String search, String rentalState, String sortType, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "creationDay"); // 기본 정렬

        if ("interestDesc".equals(sortType)) {
            sort = Sort.by(Sort.Direction.DESC, "interestCount");
        } else if ("interestAsc".equals(sortType)) {
            sort = Sort.by(Sort.Direction.ASC, "interestCount");
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        // '전체' 상태 처리
        if (rentalState == null || rentalState.equals("전체")) {
            return postRepository.searchByTitleOrContent(search, pageable);
        } else {
            return postRepository.searchByTitleOrContentAndRentalState(search, rentalState, pageable);
        }
    }


    // 게시물 추가
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    // 게시물 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Item> getItemsByPostId(Long postId) {
        // rental 테이블에서 PostID로 Item 정보를 가져오는 로직
        return rentalRepository.findItemsByPostId(postId);
    }

}
