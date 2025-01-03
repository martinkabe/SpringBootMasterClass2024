package org.example.springbootmasterclass.book;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Getter
public class BookRepository {
    private final AtomicInteger idCounter = new AtomicInteger(0);

    private final List<Book> books = new ArrayList<>();

    {
        books.add(new Book(idCounter.incrementAndGet(), "How to cook", "John Doe"));
        books.add(new Book(idCounter.incrementAndGet(), "Programming in C++","Jane Doe"));
        books.add(new Book(idCounter.incrementAndGet(), "Programming in Java","Bob Dylan"));
    }
}
