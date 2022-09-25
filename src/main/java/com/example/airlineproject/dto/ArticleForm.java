package com.example.airlineproject.dto;

import com.example.airlineproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.GsonBuilderUtils;

@AllArgsConstructor
@ToString
@Slf4j
public class ArticleForm {

    private Long id;
    private String title;
    private String content;


    public Article toEntity() {
        return new Article(id, title, content);
    }
}
