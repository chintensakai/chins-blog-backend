package com.chins.blog.backend.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "acrelation")
public class ACRelation {

  @TableId(type = IdType.AUTO)
  private Long acid;

  private Long articleId;

  private Long CategoryId;
}
