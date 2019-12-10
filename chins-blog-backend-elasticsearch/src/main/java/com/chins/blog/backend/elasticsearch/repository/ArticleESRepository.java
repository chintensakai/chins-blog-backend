package com.chins.blog.backend.elasticsearch.repository;

import com.chins.blog.backend.commons.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleESRepository extends ElasticsearchRepository<Article, Long> {

}
