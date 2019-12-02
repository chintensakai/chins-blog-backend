package com.chins.blog.backend.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chins.blog.backend.commons.entity.ACRelation;
import com.chins.blog.backend.provider.mapper.ACRelationMapper;
import com.chins.blog.backend.provider.mapper.CategoryMapper;
import com.chins.blog.backend.provider.service.ACRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ACRelationServiceImpl extends ServiceImpl<ACRelationMapper, ACRelation> implements
    ACRelationService {

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public int insertByCId(ACRelation acRelation) {

    return 0;
  }
}
