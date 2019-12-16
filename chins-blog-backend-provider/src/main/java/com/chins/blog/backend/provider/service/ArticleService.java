package com.chins.blog.backend.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chins.blog.backend.commons.base.PageMeta;
import com.chins.blog.backend.commons.base.RequestBase;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.entity.ArticleCountBean;
import com.chins.blog.backend.commons.entity.TopViewsArticle;
import com.chins.blog.backend.commons.entity.YearlyArticleCount;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ArticleService extends IService<Article> {

  List<Article> getAllArticle();

  IPage<Article> getAllArticlePage(PageMeta pageMeta);

  int insertArticleAndCategory(RequestBase requestBase);

  Map<String, Object> getArticleAndCategoryById(Long id);

  List<ArticleCountBean> getYearlyArticleCount(int year);

  List<YearlyArticleCount> getArchiveYearly();

  void incrArticleViews(RequestBase requestBase);

  Set<TopViewsArticle> rangeArticleByViews();

  List<String> searchArticleTitle(String title);
}
