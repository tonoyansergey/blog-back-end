package am.egs.controller;

import am.egs.model.bean.ArticleBean;
import am.egs.model.bean.TopicBean;
import am.egs.model.dto.User;
import am.egs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/topic")
    public ResponseEntity<List<ArticleBean>> getAllByTopic(@Valid @RequestBody final TopicBean topicBean) {
        List<ArticleBean> articles = articleService.getAllByTopic(topicBean);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ArticleBean>> getAllByUser(@Valid @RequestBody final User user) {
        List<ArticleBean> articles = articleService.getAllByUser(user);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PutMapping("/save")
    public ResponseEntity<ArticleBean> saveArticle(@Valid @RequestBody final ArticleBean article) {
        final ArticleBean articleBean = articleService.save(article);
        return new ResponseEntity<>(articleBean, HttpStatus.OK);
    }
}
