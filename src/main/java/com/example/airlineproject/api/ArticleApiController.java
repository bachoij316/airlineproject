package com.example.airlineproject.api;

import com.example.airlineproject.dto.ArticleForm;
import com.example.airlineproject.entity.Article;
import com.example.airlineproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController // RestAPI controller, return JSON
public class ArticleApiController {

    @Autowired //DI
    private ArticleRepository articleRepository;


    // GET
    @GetMapping("/api/articles") //whole list of articles
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}") //each article
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles") // post article
    public Article create(@RequestBody ArticleForm dto){
        Article article =  dto.toEntity();
        return articleRepository.save(article);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto){

        // 1: Create entity for editing
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2: Get the target entity
        Article target = articleRepository.findById(id).orElse(null);

        // 3: Wrong request handling (No target, different id)
        if (target == null || id != article.getId()){
            //400 wrong request response
            log.info("Wrong Request. id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 4: Update and return correct response
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete (@PathVariable Long id){
        // Get target
        Article target = articleRepository.findById(id).orElse(null);

        // Delete target
        if (target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Return
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
