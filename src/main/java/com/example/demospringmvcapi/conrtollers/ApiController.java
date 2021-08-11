package com.example.demospringmvcapi.conrtollers;


import com.example.demospringmvcapi.dao.ArticleDao;
import com.example.demospringmvcapi.ds.Article;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/articles",produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private ArticleDao articleDao;

    @Transactional
    @GetMapping("/creation")
    public String initData() {
        Article article1 = new Article(1, "First Article","First Article Content");
        Article article2= new Article(2, "Second Article","Second Article Content");
        Article article3 = new Article(3, "Third Article","Third Article Content");
        Article article4 = new Article(4, "Forth Article","Fourth Article Content");
        Article article5 = new Article(5, "Fifth Article","Fifth Article Content");

        articleDao.save(article1);
        articleDao.save(article2);
        articleDao.save(article3);
        articleDao.save(article4);
        articleDao.save(article5);
        return "success";
    };

    
    //curl localhost:8080/api/articles
    @GetMapping
    public ResponseEntity<Iterable<Article>> ListArticles() {
        return   ResponseEntity.ok().body(articleDao.findAll());
    }

    //curl -I localhost:8080/api/articles
    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Iterable<Article>> getArticleCount() {
        return ResponseEntity.ok()
                .header("Article-Count", String.valueOf(articleDao.count()))
                .body(articleDao.findAll());
    }

    //curl localhost:8080/api/articles/2
    @GetMapping("{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id) {
        return articleDao.findById(id)
                .map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.notFound().build());
    }

    //curl -v -X PUT localhost:8080/api/articles -H 'Content-Type: application/json' -d '{"title":"New Article","body":"New Article Content"}'
    @PutMapping
    public ResponseEntity addArticle(@RequestBody Article article) {
            articleDao.save(article);
        return ResponseEntity.ok().build();
    }

    //curl -v -X PATCH localhost:8080/api/articles/1 -H 'Content-Type: application/json' -d '{"title":"Updated Article","body":"Updated article content"}'
    @PatchMapping(value = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateArticle(@PathVariable int id, @RequestBody Article article) {

        if (articleDao.existsById(id)) {
            article.setId(id);
            articleDao.save(article);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

    //curl -v -X DELETE localhost:8080/api/articles/3
    @DeleteMapping("{id}")
    public ResponseEntity deleteArticleById(@PathVariable int id) {
        if (articleDao.existsById(id)) {
            articleDao.existsById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
