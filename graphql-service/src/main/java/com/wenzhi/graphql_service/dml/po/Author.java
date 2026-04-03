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
public class Author {
    private String id;
    private String firstName;
    private String lastName;

    // 静态作者列表 - 使用更真实的作者数据
    private static List<Author> authors = Arrays.asList(
            new Author("author-1", "J.K.", "Rowling"),
            new Author("author-2", "George R.R.", "Martin"),
            new Author("author-3", "Harper", "Lee"),
            new Author("author-4", "J.R.R.", "Tolkien"),
            new Author("author-5", "Jane", "Austen")
    );

    // 通过ID获取作者的方法
    public static Author getAuthorById(String id) {
        Optional<Author> author = authors.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
        return author.orElse(null);
    }
}