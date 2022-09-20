package com.example.airlineproject.repository;

import com.example.airlineproject.entithy.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
} //Without creating methods for CRUD, I can use them with extending CrudRepository<management object entity, UI data type of the entity>
