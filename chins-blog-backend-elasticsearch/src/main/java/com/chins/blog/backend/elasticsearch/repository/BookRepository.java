package com.chins.blog.backend.elasticsearch.repository;

import com.chins.blog.backend.elasticsearch.entity.Book;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

  List<Book> findByBookNameLike(String bookName);
}
