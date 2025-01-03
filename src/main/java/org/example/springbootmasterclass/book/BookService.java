package org.example.springbootmasterclass.book;

import org.example.springbootmasterclass.SortingOrder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(
            SortingOrder sort,
            Integer limit
    ) {
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must be a positive integer.");
        }

        if (sort == SortingOrder.ASC) {
            return bookRepository.getBooks().stream()
                    .sorted(Comparator.comparing(Book::getId))
                    .limit(limit)
                    .collect(Collectors.toList());
        }
        return bookRepository.getBooks().stream()
                .sorted(Comparator.comparing(Book::getId).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Book> getBookById(
            Integer id
    ) {
        return bookRepository.getBooks().stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst();
    }

    public void deleteBookById(Integer id) {
        bookRepository.getBooks().removeIf(book -> Objects.equals(book.getId(), id));
    }

    public void addNewBook(Book book) {
        bookRepository.getBooks().add(new Book(bookRepository.getIdCounter().incrementAndGet(), book.getTitle(), book.getAuthor()));
    }

    public Optional<Book> updateBook(
            Integer id,
            Book updatedBook
    ) {
        Optional<Book> existingBookOpt = bookRepository.getBooks().stream()
                .filter(book -> Objects.equals(book.getId(), id))
                .findFirst();

        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();

            // Create a new Book with updated fields
            Book newBook = new Book(
                    existingBook.getId(), // ID remains unchanged
                    updatedBook.getTitle() != null ? updatedBook.getTitle() : existingBook.getTitle(),
                    updatedBook.getAuthor() != null ? updatedBook.getAuthor() : existingBook.getAuthor()
            );

            // Replace the old Book in the list (assuming 'people' is modifiable)
            bookRepository.getBooks().remove(existingBook);
            bookRepository.getBooks().add(newBook);

            return Optional.of(newBook);
        } else {
            return Optional.empty();
        }
    }
}
