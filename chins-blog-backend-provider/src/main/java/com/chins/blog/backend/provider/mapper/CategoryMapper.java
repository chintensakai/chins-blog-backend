package com.chins.blog.backend.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.commons.entity.Category;
import org.apache.ibatis.annotations.Select;

public interface CategoryMapper extends BaseMapper<Category> {

  @Select("SELECT id FROM chinsblog.category where category_name = #{cName}")
  Long queryCategoryIdByName(String cName);

  @Select("SELECT category_name FROM chinsblog.category where id=#{id}")
  String getCategoryNameById(Long id);
}
