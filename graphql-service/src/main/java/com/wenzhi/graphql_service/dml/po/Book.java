package com.wenzhi.graphql_service.dml.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String id;
    private String name;
    private int pageCount;
    private String authorId;

    // 静态书籍列表 - 使用更真实的书籍数据
    private static List<Book> books = Arrays.asList(
            new Book("book-1", "Harry Potter and the Philosopher's Stone", 223, "author-1"),
            new Book("book-2", "A Game of Thrones", 694, "author-2"),
            new Book("book-3", "To Kill a Mockingbird", 336, "author-3"),
            new Book("book-4", "The Hobbit", 310, "author-4"),
            new Book("book-5", "Pride and Prejudice", 480, "author-5")
    );

    // 通过ID获取书籍的方法
    public static Book getBookById(String id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        return book.orElse(null);
    }
}
