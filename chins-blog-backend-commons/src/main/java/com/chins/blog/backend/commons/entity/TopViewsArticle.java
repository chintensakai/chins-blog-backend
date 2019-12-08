package com.chins.blog.backend.commons.entity;

import lombok.Data;

@Data
public class TopViewsArticle {

  private Long id;
  private String title;
  private int views;
}
