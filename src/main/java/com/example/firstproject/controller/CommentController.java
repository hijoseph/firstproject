package com.example.firstproject.controller;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService; // 댓글 서비스 객체 주입
    @Autowired
    private CommentRepository commentRepository;

    // 1. 댓글 조회
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto){
        // 1. service에 위임
        CommentDto createDto = commentService.create(articleId, dto);

        // 2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    // 3. 댓글 수정

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> update(@PathVariable Long commentId, @RequestBody CommentDto dto){
        // 1. 서비스에 위임
        CommentDto updateDto = commentService.update(commentId, dto);

        // 2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 4. 댓글 삭제

    @DeleteMapping("/comments/{deleteId}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long deleteId){
        // 1. 서비스에 위임
        CommentDto deleteDto = commentService.delete(deleteId);

        // 2. 결과 응답
        return null;
    }
}
