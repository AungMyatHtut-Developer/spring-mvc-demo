package com.example.demospringmvcapi.dao;

import com.example.demospringmvcapi.ds.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends CrudRepository<Article,Integer> {


    List<Article> findByBodyLikeIgnoreCase(String content);
}
