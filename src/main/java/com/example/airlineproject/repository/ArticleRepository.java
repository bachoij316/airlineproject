package com.example.airlineproject.repository;

import com.example.airlineproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
} //Without creating methods for CRUD, I can use them with extending CrudRepository<management object entity, UI data type of the entity>
