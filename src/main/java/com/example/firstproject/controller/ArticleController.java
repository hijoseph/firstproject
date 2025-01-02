package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired // Repository 등록, 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    // Insert View
    @GetMapping("/articles/new")
    public String newArticleForm(Model model, ArticleForm form) {
        model.addAttribute("titleheader", "article New");
        return "articles/new";
    }

    // Insert Action
    // 등록시 DB에 저장하고 / 저장된 데이타를 출력하는 show로 redirect한다.
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form, Model model) {
        log.info(form.toString()); // ArticleForm(id=null, title=제목1, content=내용1)

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString()); // Article{id=null, title='제목1', content='내용1'} - DTO가 앤티티로 잘 변환되는지 확인 출력

        // 2. Repository로 Entity를 DB에 저장
        Article saved = articleRepository.save(article); // Saved article entity, Return to saved Object
        // 1. select next value for article_seq
        // 2. insert into article(content,title,id)
        //           values (?,?,?) <- Article{id=1, title='제목1', content='내용1'}
        log.info(saved.toString()); // Article{id=1, title='제목1', content='내용1'} - article DB에 잘 저장되는지 확인 출력

        model.addAttribute("titleheader", "article Create");

        return "redirect:/articles/" + saved.getId();
    }

    // select List
    @GetMapping("/articles/{id}")  // A1. 서버의 컨트롤러가 URL요청을 받는다.
    public String show(@PathVariable Long id, Model model) {
        log.info("id: " + id); // A2. @PathVariable를 통해 넘겨진 Parameter를 확인


        // B1. id를 조회해 데이터 가져오기 (findById)
        Article articleEntity = articleRepository.findById(id).orElse(null); // 조회한 값이 없으면 null값을 반환
        log.info("##########after articleEntity ######");
        log.info("articleEntity: " + articleEntity);

        // JPA의 CrudRepository가 제공하는 메서드로, 특정 엔티티의 id값을 기준으로 데이터를 찾아 Optional타입으로 반환
        List<CommentDto> commentsDtos = commentService.comments(id);

        log.info("##########  after commentsDtos  ##########");
        log.info("commentsDtos : " + commentsDtos);
        // B2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos); // 댓글 목록 모델에 등록

        // B3. 뷰 페이지 반환하기
        model.addAttribute("titleheader", "article show");
        return "articles/show";
    }

    // List
    @GetMapping("/articles")
    public String index(Model model) {

        // C1. DB에서 모든 Article 데이터 가져오기 (findAll)
/*      List<Article> articleEntityList1 = (List<Article>) articleRepository.findAll();
        Iterable<Article> articleEntityList2 = articleRepository.findAll(); */
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        // JPA의 CrudRepository가 제공하는 메서드로, 특정 엔티티를 모두 가져와 Iterable타입으로 반환
        // C2. 가져온 Article묶음을 모델에 등록하기
        model.addAttribute("articleList", articleEntityList);
        model.addAttribute("titleheader", "article List");

        // C3. 사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";
    }

    // select Edit
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        model.addAttribute("titleheader", "article edit"); // title

        // D1. 뷰 페이지 설정하기
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    // Update Action
    @PostMapping("/articles/update")
    public String update(ArticleForm form, Model model) {
        log.info(form.toString());

        // E1. DTO를 엔티티로 변환하기
        Article UpdateArticleEntity = form.toEntity();
        log.info(UpdateArticleEntity.toString());

        // E2. 엔티티를 DB에 저장하기
        Article target = articleRepository.findById(UpdateArticleEntity.getId()).orElse(null);
        if (target != null) {
            articleRepository.save(UpdateArticleEntity);
        }
        // E3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + UpdateArticleEntity.getId();
    }

    // Delete Action
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // F1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);  // 데이터 찾기
        log.info(target.toString());

        // F2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제했습니다.");
        }
        // F3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }


}
