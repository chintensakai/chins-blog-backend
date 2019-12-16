package com.chins.blog.backend.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chins.blog.backend.commons.base.PageMeta;
import com.chins.blog.backend.commons.base.RequestBase;
import com.chins.blog.backend.commons.base.ResponseBase;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.entity.ArticleCountBean;
import com.chins.blog.backend.commons.entity.TopViewsArticle;
import com.chins.blog.backend.commons.entity.YearlyArticleCount;
import com.chins.blog.backend.provider.service.ArticleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping("/all")
  public <T> ResponseBase getAllList() {

    List<Article> allArticle = articleService.getAllArticle();
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("list", allArticle);
    return ResponseBase.success(resultMap);
  }

  @PostMapping("/postarticle")
  public <T> ResponseBase postArticle(@RequestBody RequestBase request) {

    int i = articleService.insertArticleAndCategory(request);
    return ResponseBase.success(i);
  }

  @GetMapping("/{id}")
  public <T> ResponseBase getArticleDetailById(@PathVariable Long id) {

    Map<String, Object> articleAndCategoryById = articleService.getArticleAndCategoryById(id);
    return ResponseBase.success(articleAndCategoryById);
  }

  @GetMapping("/archive/{year}")
  public <T> ResponseBase getYearlyArticle(@PathVariable int year) {

    List<ArticleCountBean> yearlyArticleCount = articleService.getYearlyArticleCount(year);

    return ResponseBase.success(yearlyArticleCount);
  }

  @GetMapping("/archive")
  public <T> ResponseBase getArchiveArticle() {

    List<YearlyArticleCount> archiveYearly = articleService.getArchiveYearly();

    return ResponseBase.success(archiveYearly);
  }

  @PostMapping("/increviews")
  public <T> ResponseBase articleViewsIncre(@RequestBody RequestBase requestBase) {

    articleService.incrArticleViews(requestBase);

    return ResponseBase.success(null);
  }

  @GetMapping("/topviews")
  public <T> ResponseBase getTopViewsArticle() {

    Set<TopViewsArticle> topViewsArticles = articleService.rangeArticleByViews();

    return ResponseBase.success(topViewsArticles);
  }

  @GetMapping("/search")
  public <T> ResponseBase searchArticleTitle(@RequestParam String title) {

    List<Map<String, Object>> resultList = new ArrayList<>();

    List<String> articles = articleService.searchArticleTitle(title);

    for (String s : articles) {
      Map<String, Object> map = new HashMap<>();
      map.put("value", s);
      resultList.add(map);
    }
    return ResponseBase.success(resultList);
  }

  @PostMapping("/page")
  public ResponseBase getArticlePage(@RequestBody PageMeta pageMeta) {

    IPage<Article> allArticlePage = articleService.getAllArticlePage(pageMeta);

    return ResponseBase.success(allArticlePage);
  }
}
