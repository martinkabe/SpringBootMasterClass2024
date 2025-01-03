package org.example.springbootmasterclass.book;

import org.example.springbootmasterclass.SortingOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books2")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") SortingOrder sort,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return bookService.getAllBooks(sort, limit);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Book>> getBookById(
            @PathVariable("id") Integer id
    ) {
        Optional<Book> book = bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping("{id}")
    public void deleteBookById(@PathVariable("id") Integer id) {
        bookService.deleteBookById(id);
    }

    @PostMapping
    public void addNewBook(@RequestBody Book book) {
        bookService.addNewBook(book);
    }

    @PutMapping("{id}")
    public ResponseEntity<Optional<Book>> updateBook(
            @PathVariable("id") Integer id,
            @RequestBody Book updatedBook
    ) {
        Optional<Book> book = bookService.updateBook(id, updatedBook);
        if (book.isPresent()) {
            return ResponseEntity.ok().body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
