package com.chins.blog.backend.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chins.blog.backend.commons.entity.Category;
import com.chins.blog.backend.provider.mapper.CategoryMapper;
import com.chins.blog.backend.provider.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements
    CategoryService {

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public Long getCategoryIdByCName(String categoryName) {

    Long categoryId = categoryMapper.queryCategoryIdByName(categoryName);

    return categoryId;
  }
}
