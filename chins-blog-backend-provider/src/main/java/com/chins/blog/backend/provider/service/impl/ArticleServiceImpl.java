package com.chins.blog.backend.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chins.blog.backend.commons.base.RequestBase;
import com.chins.blog.backend.commons.entity.ACRelation;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.utils.JSONUtils;
import com.chins.blog.backend.provider.mapper.ACRelationMapper;
import com.chins.blog.backend.provider.mapper.ArticleMapper;
import com.chins.blog.backend.provider.mapper.CategoryMapper;
import com.chins.blog.backend.provider.service.ArticleService;
import java.util.List;
import java.util.Map;
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

  @Override
  public List<Article> getAllArticle() {
    return articleMapper.selectList(null);
  }

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
      System.out.println("---------- categoryId " + categoryId);

      ACRelation acRelation = new ACRelation();
      acRelation.setArticleId(article.getId());
      acRelation.setCategoryId(categoryId);

      int insert = acRelationMapper.insert(acRelation);
      System.out.println(insert);
    }

    return 0;
  }

}
