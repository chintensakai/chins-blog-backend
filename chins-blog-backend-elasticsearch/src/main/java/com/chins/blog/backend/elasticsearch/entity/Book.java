package com.chins.blog.backend.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "gf", type = "book")
@Data
public class Book {

  private Integer id;
  private String bookName;
  private String author;
}
