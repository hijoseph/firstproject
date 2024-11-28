package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ApiRepository;
import com.example.firstproject.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;
    @Autowired
    private ApiRepository apiRepository;

    // List All Data
    @GetMapping("/edwin/api/articles")
    public List<Article> index(){
        return apiService.index();
    }

    // List One Data
    @GetMapping("/edwin/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return apiService.show(id);
    }

    // Create One Data
    @PostMapping("/edwin/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm articleForm){
        // 1. 서비스로 게시글 생성
        Article created = apiService.create(articleForm);

        // 2. 생성하면 정상, 실패하면 오류 응답
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/edwin/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm articleForm){
        // 1. 서비스를 통해 게시글 수정
        Article updated = apiService.update(id, articleForm);
        // 2. 수정되면 정상, 안되면 오류 응답
        return (updated != null)
                ? ResponseEntity.status(HttpStatus.OK).body(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/edwin/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 1. 서비스를 통해 게시글 삭제
        Article deleted = apiService.delete(id);
        // 2. 삭제 결과에 따라 응답처리
        return (deleted != null)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 여러 게시글 생성 요청 접수, transactionTest() 메서드 정의
    @PostMapping("/edwin/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> lArticleForm){

        // 1. 서비스 호출
        List<Article> createdList = apiService.createArticles(lArticleForm);
        // 2. 생성 결과에 따라 응답처리
        return (createdList != null)
                ? ResponseEntity.status(HttpStatus.OK).body(createdList)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
