package com.chins.blog.backend.elasticsearch.repository;

import com.chins.blog.backend.commons.entity.Article;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleESRepository extends ElasticsearchRepository<Article, Long> {

  List<Article> findByTitleLike(String title);

}
