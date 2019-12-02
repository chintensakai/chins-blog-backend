package com.chins.blog.backend.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chins.blog.backend.commons.base.RequestBase;
import com.chins.blog.backend.commons.entity.Article;
import java.util.List;

public interface ArticleService extends IService<Article> {

  List<Article> getAllArticle();

  int insertArticleAndCategory(RequestBase requestBase);


}
