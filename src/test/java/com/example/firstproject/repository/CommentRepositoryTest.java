package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
    /* Case 1 : 4번 게시글의 모든 댓글 조회 */
        {

            // 1. 실제 데이터
            Long articleId = 1L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 2. 예상 데이터

            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");
            Comment a = new Comment(1L, article, "Park", "굿 월 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");
            List<Comment> expected = Arrays.asList(a,b,c);

           /* Article article = new Article(1L, "제목1", "내용1");
            List<Article> expected = Arrays.asList(article);*/

            // 3. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */

        // 1. 실제 데이터
            String nickName = "Park";
            List<Comment> comments = commentRepository.findByNickname(nickName);

        // 2. 예상 데이터
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickName, "굿 월 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickName, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickName, "조깅");

            List<Comment> expected = Arrays.asList(a,b,c);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(),"Park의 모든 댓글을 출력!");
    }
}