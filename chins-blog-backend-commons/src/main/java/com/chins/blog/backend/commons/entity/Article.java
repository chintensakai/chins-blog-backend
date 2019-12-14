package com.chins.blog.backend.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "chinsblog_article", type = "article")
@Data
public class Article {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  private String title;
  private String content;
  private String date;
  private int views;
  private int comments;
  private int thumbs;
}
