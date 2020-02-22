package libraryManager.controllers.all;

import libraryManager.entity.full.FullBook;
import libraryManager.service.book.BookItemCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/library/search/book")
public class SearchBookController {

    @Autowired
    private BookItemCatalog bookItemCatalog;

    @GetMapping("/author/name/{name}/surname/{surname}")
    ResponseEntity<List<FullBook>> findByAuthor(@PathVariable String name, @PathVariable String surname) {
        return ResponseEntity.ok(bookItemCatalog.findBookByAuthor(name, surname));
    }

    @GetMapping("/title/{title}")
    ResponseEntity<List<FullBook>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookItemCatalog.findBookByTitle(title));
    }

    @GetMapping("/isbn/{isbn}")
    ResponseEntity<FullBook> findByIsbn(@PathVariable Long isbn) {
        FullBook byIsnb = bookItemCatalog.findBookByIsbn(isbn);
        if (byIsnb != null) {
            return ResponseEntity.<FullBook>ok(byIsnb);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


