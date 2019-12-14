package com.chins.blog.backend.test;

import com.chins.blog.backend.commons.entity.Article;
import com.chins.blog.backend.elasticsearch.entity.Book;
import com.chins.blog.backend.elasticsearch.repository.ArticleESRepository;
import com.chins.blog.backend.elasticsearch.repository.BookRepository;
import com.chins.blog.backend.provider.mapper.ArticleMapper;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBlogBackendApplication {

  @Autowired
  BookRepository bookRepository;

  @Autowired
  ArticleMapper articleMapper;

  @Autowired
  ArticleESRepository articleESRepository;

  @Test
  public void createIndex2() {
    Book book = new Book();
    book.setId(1);
    book.setBookName("西游记");
    book.setAuthor("吴承恩");
    bookRepository.index(book);
  }

  @Test
  public void useFind() {
    List<Book> list = bookRepository.findByBookNameLike("游");
    for (Book book : list) {
      System.out.println(book);
    }
  }

  @Test
  public void useFind2() {
    List<Article> list = articleESRepository.findByTitleLike("canal");
    for (Article article : list) {
      System.out.println(article);
    }
  }

  @Test
  public void createArticleIndex() {

    List<Article> articles = articleMapper.selectList(null);
    for (Article article : articles) {
      articleESRepository.index(article);
    }
  }

}
