package com.wenzhi.graphql_service.controller;

import com.wenzhi.graphql_service.dml.po.Book;
import com.wenzhi.graphql_service.dml.po.Author;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    /**
     * 根据ID查询书籍
     * @param id 书籍ID
     * @return 对应的书籍对象，如果未找到则返回null
     */
    @QueryMapping
    public Book bookById(@Argument(name = "id") String id) {  // 明确指定参数名称
        logger.info("查询书籍，ID: {}", id);
        Book book = Book.getBookById(id);

        if (book == null) {
            logger.warn("未找到ID为 {} 的书籍", id);
        } else {
            logger.info("找到书籍: {}", book.getName());
        }

        return book;
    }

    /**
     * 为书籍查询关联的作者信息
     * @param book 书籍对象
     * @return 书籍对应的作者对象
     */
    @SchemaMapping
    public Author author(Book book) {
        if (book == null) {
            logger.warn("书籍对象为null，无法查询作者");
            return null;
        }

        logger.info("查询书籍《{}》的作者，作者ID: {}", book.getName(), book.getAuthorId());
        Author author = Author.getAuthorById(book.getAuthorId());

        if (author == null) {
            logger.warn("未找到ID为 {} 的作者", book.getAuthorId());
        }

        return author;
    }
}

/*
* http://localhost:8080/graphiql
* */