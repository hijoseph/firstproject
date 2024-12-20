package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // 로깅할 수 있게 어노테이션 추가
@Service // 서비스 객체 선언
public class ApiService {
    @Autowired
    private ApiRepository apiRepository;

    // 1-1. 전체 조회
    public List<Article> index(){
        return apiRepository.findAll();
    }

    // 1-2. 조건 조회
    public Article show(Long id) {
        return apiRepository.findById(id).orElse(null);
    }

    // 2. 입력
    public Article create(ArticleForm articleForm) {
        Article article = articleForm.toEntity();
        if(article.getId() != null){
            return null;
        }
        return apiRepository.save(article);
    }

    // 3. 수정
    public Article update(Long id, ArticleForm articleForm) {

        // 1. DTO -> Entity 변환하기
        Article article = articleForm.toEntity();
        log.info("id : {} ,  articleForm : {} ", id, articleForm);

        // 2. 타깃 조회하기
        Article target = apiRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리하기
        if (target == null || id != article.getId()) {
            return null; // 응답은 컨트롤러가 하므로 여기서는 null반환
        }

        // 4 엡데이트하기
        target.patch(article);
        Article updated = apiRepository.save(target);
        return updated; // 응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
    }

    // 4. 삭제
    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = apiRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return null; // 응답은 컨트롤러가 하므로 여기서는 null반환
        }

        // 3. 대상 삭제하기
        apiRepository.delete(target);
        return target; // DB에서 삭제한 대상을 컨트롤러에 반환
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {

        // 1. dto 묶음(리스트)을 엔티티 묶음(리스트)으로 변환하기
        // dtos를 스트림화 한다, 최종 결과를 articleList에 저장한다
        List<Article> articleList = dtos.stream()
        // map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해 매핑한다
                .map(dto -> dto.toEntity())
                // 이렇게 매핑한 것을 리스트로 묶는다
                .collect(Collectors.toList());

       /*스트림 문법은 리스트와 같은 자료구조에서 저장된 요소를
        * 하나씩 순화하면서 처리하는 코드 패턴입니다.
        * 위의 스트림 문법은 아래와 동일하다
        * List<Article> articleList = new ArrayList<>();
        * for(int i=0; i<dtos.size(); i++){
        *   ArticleForm dto = dtos.get(i);
        *   Article entity = dto.toEntity();
        *   articleList.add(entity);
        * }
        */

        // 2. 엔티티 묶음(리스트)을 DB에 저장하기
        articleList.stream()
                .forEach(article -> apiRepository.save(article));

        // 3. 강제 예외 발생시키기
        apiRepository.findById(-1L)
                .orElseThrow(()-> new IllegalArgumentException("결제 실패!"));

        // 4. 결과 값 반환하기
        return articleList;

    }

/*
    public List<Article> createArticles2(List<ArticleForm> dtos){
        List<Article> articleList = dtos.stream()
                .map(dto-> dto.toEntity())
                .collect(Collectors.toList());

        articleList.stream()
                .forEach(article -> apiRepository.save(article));
        apiRepository.findById(-1L)
                .orElseThrow(()-> new IllegalArgumentException("결제 실패"));
        return articleList;
    }
*/


}
