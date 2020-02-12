package libraryManager.controllers.librarian;

import libraryManager.entity.Book;
import libraryManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookManageController {

        @Autowired
        private BookRepository bookRepository;

        @PostMapping("/library/book")
        ResponseEntity<Void> newBook(@RequestBody Book book) {
            if (bookRepository.save(book)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @GetMapping("/library/books")
        List<Book> all() {
            return bookRepository.findAll();
        }

        @GetMapping("/library/book/{isbn}")
        ResponseEntity<Book> book(@PathVariable Long isbn) {
            return bookRepository.findByIsbn(isbn)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("library/book/{isbn}")
        void deleteAuthor(@PathVariable Long isbn) {
            bookRepository.deleteByIsbn(isbn);
        }

    }

