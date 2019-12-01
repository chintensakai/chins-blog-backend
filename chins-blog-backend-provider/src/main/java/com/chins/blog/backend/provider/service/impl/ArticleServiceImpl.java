package com.chins.blog.backend.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.provider.mapper.ArticleMapper;
import com.chins.blog.backend.provider.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements
    ArticleService {

  @Autowired
  private ArticleMapper articleMapper;

  @Override
  public List<Article> getAllArticle() {
    return articleMapper.selectList(null);
  }
}
