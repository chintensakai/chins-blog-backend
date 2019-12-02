package com.chins.blog.backend.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chins.blog.backend.commons.entity.ACRelation;

public interface ACRelationService extends IService<ACRelation> {

  int insertByCId(ACRelation acRelation);
}
