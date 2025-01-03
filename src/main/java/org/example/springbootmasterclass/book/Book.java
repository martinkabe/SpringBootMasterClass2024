package org.example.springbootmasterclass.book;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String author;
}
