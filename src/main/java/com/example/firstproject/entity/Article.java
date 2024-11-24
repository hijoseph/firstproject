package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor // public Article(long id, String title, String content){}
@NoArgsConstructor  // public Article(){}
@ToString // 모든 필드 출력
@Entity // 앤티티 선언
@Getter
public class Article {
    @Id // 엔티티의 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동생성
    private Long id;

    @Column // title 필드 선언, DB테이블의 title열과 연결됨
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if (article.title != null){
            this.title = article.title;
        }
        if (article.content != null){
            this.content = article.content;
        }
    }

/* -- @AllArgsConstructor
    public Article(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    } */
}
