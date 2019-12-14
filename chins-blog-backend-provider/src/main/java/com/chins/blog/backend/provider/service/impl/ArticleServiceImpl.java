package com.chins.blog.backend.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chins.blog.backend.cache.utils.RedisUtils;
import com.chins.blog.backend.commons.base.RequestBase;
import com.chins.blog.backend.commons.entity.ACRelation;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.entity.ArticleCountBean;
import com.chins.blog.backend.commons.entity.TopViewsArticle;
import com.chins.blog.backend.commons.entity.YearlyArticleCount;
import com.chins.blog.backend.commons.utils.JSONUtils;
import com.chins.blog.backend.elasticsearch.repository.ArticleESRepository;
import com.chins.blog.backend.provider.mapper.ACRelationMapper;
import com.chins.blog.backend.provider.mapper.ArticleMapper;
import com.chins.blog.backend.provider.mapper.CategoryMapper;
import com.chins.blog.backend.provider.service.ArticleService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements
    ArticleService {

  @Autowired
  private ArticleMapper articleMapper;

  @Autowired
  private CategoryMapper categoryMapper;

  @Autowired
  private ACRelationMapper acRelationMapper;

  @Autowired
  private ArticleESRepository articleESRepository;

  private static final String ARTICLE_VIEWS_RANGE_KEY = "article:views:range";

  @Override
  public List<Article> getAllArticle() {
    return articleMapper.selectList(null);
  }

  @Autowired
  private RedisUtils redisUtils;

  @Override
  public int insertArticleAndCategory(RequestBase requestBase) {

    Map<String, Object> requestData = requestBase.getData();
    Map<String, Object> articleMap = (Map<String, Object>) requestData.get("article");

    String articleStr = JSONUtils.mapToJson(articleMap);
    Article article = JSONUtils.jsonToObject(articleStr, Article.class);

    int insertResult = articleMapper.insert(article);
//    if (insertResult != 0) {
//      Long id = article.getId();
//
//      System.out.println(id);
//    }

    List<String> categorys = (List<String>) requestData.get("categorys");

    for (String category :
        categorys) {

      Long categoryId = categoryMapper.queryCategoryIdByName(category);

      ACRelation acRelation = new ACRelation();
      acRelation.setArticleId(article.getId());
      acRelation.setCategoryId(categoryId);

      int insert = acRelationMapper.insert(acRelation);
      System.out.println(insert);
    }

    return 0;
  }

  @Override
  public Map<String, Object> getArticleAndCategoryById(Long id) {
    Article article = articleMapper.selectById(id);
    List<Long> categoryIdByArticleId = acRelationMapper.getCategoryIdByArticleId(id);

    List<String> categoryNames = new ArrayList<>();

    for (Long categoryId :
        categoryIdByArticleId) {
      categoryNames.add(categoryMapper.getCategoryNameById(categoryId));
    }

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("article", article);
    resultMap.put("categoryList", categoryNames);

    return resultMap;
  }

  @Override
  public List<ArticleCountBean> getYearlyArticleCount(int year) {

    String YearStart = year + "-1-1";
    String YearEnd = year + "-12-31";

    List<ArticleCountBean> yearlyArticleCounts = articleMapper
        .selectYearlyArticles(YearStart, YearEnd);

    return yearlyArticleCounts;
  }

  @Override
  public List<YearlyArticleCount> getArchiveYearly() {

    List<YearlyArticleCount> resultList = new ArrayList<>();

    LocalDate localDate = LocalDate.now();
    int currentYear = localDate.getYear();
    List<Integer> years = Arrays
        .asList(currentYear, currentYear - 1, currentYear - 2, currentYear - 3);

    for (Integer year :
        years) {
      YearlyArticleCount yearlyArticleCount = new YearlyArticleCount();
      yearlyArticleCount.setYear(year);
      yearlyArticleCount.setCount(articleMapper.selectYearlyArchive(year));
      resultList.add(yearlyArticleCount);
    }

    return resultList;
  }

  @Override
  public void incrArticleViews(RequestBase requestBase) {

    Map<String, Object> data = requestBase.getData();
    Long id = Long.valueOf(String.valueOf(data.get("id")));

    Set<TopViewsArticle> set = redisUtils.sortSetRange(ARTICLE_VIEWS_RANGE_KEY, 0, 10);
    for (TopViewsArticle viewsArticle :
        set) {
      if (viewsArticle.getId() == id) {
        redisUtils.sortSetZincr(ARTICLE_VIEWS_RANGE_KEY, viewsArticle, 1);
      }
    }

    articleMapper.increArticleViewwById(id);
  }

  @Override
  public Set<TopViewsArticle> rangeArticleByViews() {

    List<Article> articles = null;
    if (!redisUtils.existsKey(ARTICLE_VIEWS_RANGE_KEY)) {
      articles = articleMapper.selectList(null);

      for (Article article :
          articles) {

        TopViewsArticle topViewsArticle = new TopViewsArticle();
        topViewsArticle.setTitle(article.getTitle());
        topViewsArticle.setId(article.getId());
        redisUtils.sortSetAdd(ARTICLE_VIEWS_RANGE_KEY, article.getViews(), topViewsArticle);
      }
    }

    Set<TopViewsArticle> viewsSet = redisUtils.sortSetRange(ARTICLE_VIEWS_RANGE_KEY, 0, 10);

    for (TopViewsArticle topViewsArticle :
        viewsSet) {

      Double aDouble = redisUtils.sortSetScore(ARTICLE_VIEWS_RANGE_KEY, topViewsArticle);
      topViewsArticle.setViews(aDouble.intValue());
    }

    return viewsSet;
  }

  @Override
  public List<String> searchArticleTitle(String title) {

//    CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders
//        .completionSuggestion("title").prefix(title).size(5);
//
//    SuggestBuilder suggestBuilder = new SuggestBuilder();
//    suggestBuilder.addSuggestion("suggest", completionSuggestionBuilder);

    List<Article> byTitleLike = articleESRepository.findByTitleLike(title);
    List<String> titleList = new ArrayList<>();
    for (Article article : byTitleLike) {
      titleList.add(article.getTitle());
    }
    return titleList;
  }
}
