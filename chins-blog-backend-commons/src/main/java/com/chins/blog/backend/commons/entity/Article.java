package com.chins.blog.backend.commons.entity;

import lombok.Data;

@Data
public class Article {

  private Long id;
  private String title;
  private String content;
  private String date;
  private int categoryId;
  private int views;
  private int comments;
  private int thumbs;
}
