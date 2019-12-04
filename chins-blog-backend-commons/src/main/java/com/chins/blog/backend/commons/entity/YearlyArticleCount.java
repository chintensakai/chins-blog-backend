package com.chins.blog.backend.commons.entity;

import java.sql.Date;
import lombok.Data;

@Data
public class YearlyArticleCount {

  private String title;
  private Date date;
}
