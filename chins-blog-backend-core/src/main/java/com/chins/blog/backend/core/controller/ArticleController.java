package com.chins.blog.backend.core.controller;

import com.chins.blog.backend.commons.base.RequestBase;
import com.chins.blog.backend.commons.base.ResponseBase;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.entity.ArticleCountBean;
import com.chins.blog.backend.commons.entity.YearlyArticleCount;
import com.chins.blog.backend.provider.service.ArticleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping("/articles")
  public <T> ResponseBase getAllList() {

    List<Article> allArticle = articleService.getAllArticle();
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("list", allArticle);
    return new ResponseBase(200, "success", resultMap);
  }

  @PostMapping("/postarticle")
  public <T> ResponseBase postArticle(@RequestBody RequestBase request) {

    int i = articleService.insertArticleAndCategory(request);
    return null;
  }

  @GetMapping("/article/{id}")
  public <T> ResponseBase getArticleDetailById(@PathVariable Long id) {

    Map<String, Object> articleAndCategoryById = articleService.getArticleAndCategoryById(id);
    return new ResponseBase(200, "success", articleAndCategoryById);
  }

  @GetMapping("/archive/{year}")
  public <T> ResponseBase getYearlyArticle(@PathVariable int year) {

    List<ArticleCountBean> yearlyArticleCount = articleService.getYearlyArticleCount(year);

    return new ResponseBase(200, "success", yearlyArticleCount);
  }

  @GetMapping("/archive")
  public <T> ResponseBase getArchiveArticle() {

    List<YearlyArticleCount> archiveYearly = articleService.getArchiveYearly();

    return new ResponseBase(200, "success", archiveYearly);
  }

  @PostMapping("/article/increviews")
  public <T> ResponseBase articleViewsIncre(@RequestBody RequestBase requestBase) {

    System.out.println("============== " + requestBase);

    articleService.increArticleViews(requestBase);

    return new ResponseBase(200, "success", null);
  }
}
