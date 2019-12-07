package com.chins.blog.backend.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.commons.entity.ArticleCountBean;
import com.chins.blog.backend.commons.entity.YearlyArticleCount;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface ArticleMapper extends BaseMapper<Article> {

  @Select("SELECT title, date FROM chinsblog.article where date between #{start} AND #{end}")
  @ResultType(ArticleCountBean.class)
  List<ArticleCountBean> selectYearlyArticles(@Param("start") String yearStart,
      @Param("end") String yearEnd);

  //  @Select("<script> SELECT count(*) FROM chinsblog.article where date between <foreach collection='years' item='year' separator=','> CONCAT(#{year}, '-1-1') AND CONCAT(#{year}, '-12-31') </foreach></script>")
//  foreach sql不是用在这种场景中，是用来多条件查询的
  @Select("SELECT count(*) FROM chinsblog.article where date between CONCAT(#{year}, '-1-1') AND CONCAT(#{year}, '-12-31')")
  @ResultType(YearlyArticleCount.class)
  int selectYearlyArchive(@Param("year") int years);
}
