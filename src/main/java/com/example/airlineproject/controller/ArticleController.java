package com.example.airlineproject.controller;

import com.example.airlineproject.dto.ArticleForm;
import com.example.airlineproject.entity.Article;
import com.example.airlineproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {

    @Autowired //No need to make object because Spring Boot automatically do that for me
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        log.info("newArticleForm");
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info("DTO");
        log.info(form.toString());

        // 1. Convert Dto to Entity
        Article article = form.toEntity();
        log.info("Entity");
        log.info(article.toString());

        // 2. With Repository, save Entity into DB
        Article saved = articleRepository.save(article);
        log.info("Saved in DB");
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id =" + id); //Check the url with variable {id} works
        // 1: Pull data with given id
        Article articleEntity = articleRepository.findById(id).orElse(null); //"Optional<Article> articleEntity" also works

        // 2: Place the data to the model
        model.addAttribute("article",articleEntity);

        // 3: Set up the page to show
        return"articles/show";

    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1: Get all articles
        List<Article> articleEntityList = articleRepository.findAll();

        // 2: Transfer the articles to View
        model.addAttribute("articleList",articleEntityList);

        // 3: Set up View page
        return "articles/index"; //articles/index.mustache
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // Get data to edit
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // Put data into the model
        model.addAttribute("article",articleEntity);

        // Setting VIEW Page
        return "articles/edit";

    }

    @PostMapping("/articles/update") // Supposed to be PatchMapping but Patch is not supported so use Post
    public String update(ArticleForm form){
        log.info("dto update values");
        log.info(form.toString());

        // 1: Convert the received DTO to entity
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: Store the entity to DB
        // 2-1: Get exist data from DB
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null); // Data exist? return the id, no data? return null

        // 2-2: if data exist, update it
        if (target != null){
            articleRepository.save(articleEntity); // entity updated in DB
        }

        // 3: Redirect to edited done page
         return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("Deleted request detected");

        // 1: Get deleted target
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2: Delete the target
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","Delete Successfully");
        }
        log.info("Deleted successfully");

        // 3: Redirect to the result page
        return "redirect:/articles/";
    }
}
