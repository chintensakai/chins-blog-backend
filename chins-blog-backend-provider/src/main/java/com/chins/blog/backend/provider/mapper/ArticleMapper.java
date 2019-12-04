package com.chins.blog.backend.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.entity.YearlyArticleCount;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface ArticleMapper extends BaseMapper<Article> {

  @Select("SELECT title, date FROM chinsblog.article where date between #{start} AND #{end}")
  @ResultType(YearlyArticleCount.class)
  List<YearlyArticleCount> selectYearlyArticles(@Param("start") String yearStart,
      @Param("end") String yearEnd);
}
