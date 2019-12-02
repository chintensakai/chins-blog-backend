package com.chins.blog.backend.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.commons.entity.ACRelation;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface ACRelationMapper extends BaseMapper<ACRelation> {

  /***
   * 通过文章id，查询文章所属的类别id
   * @param articleId
   * @return
   */
  @Select("SELECT category_id FROM chinsblog.acrelation where article_id=#{articleId};")
  List<Long> getCategoryIdByArticleId(Long articleId);
}
