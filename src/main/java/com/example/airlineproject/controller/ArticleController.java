package com.example.airlineproject.controller;

import com.example.airlineproject.dto.ArticleForm;
import com.example.airlineproject.entithy.Article;
import com.example.airlineproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired //No need to make object because Spring Boot automatically do that for me
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        System.out.println("Dto");
        System.out.println(form.toString());

        // 1. Convert Dto to Entity
        Article article = form.toEntity();
        System.out.println("Entity");
        System.out.println(article.toString());

        // 2. With Repository, save Entity into DB
        Article saved = articleRepository.save(article);
        System.out.println("Saved in DB");
        System.out.println(saved.toString());
        return "";
    }

}
