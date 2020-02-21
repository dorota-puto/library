package libraryManager.controllers.all;

import libraryManager.entity.full.FullBookItem;
import libraryManager.service.book.BookItemCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/library/search/bookitem")
public class FullBookItemController {

    @Autowired
    private BookItemCatalog bookItemCatalog;

    @GetMapping("/isbn/{isbn}")
    ResponseEntity<List<FullBookItem>> findByIsbn(@PathVariable Long isbn) {
        List<FullBookItem> byIsbn = bookItemCatalog.findByIsbn(isbn);
        if (byIsbn != null) {
            return ResponseEntity.ok(byIsbn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rfidTag/{rfidTag}")
    ResponseEntity<FullBookItem> findByRfidTag(@PathVariable String rfidTag) {
        FullBookItem byRfidTag = bookItemCatalog.findByRfidTag(rfidTag);
        if (byRfidTag != null) {
            return ResponseEntity.<FullBookItem>ok(byRfidTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title/{title}")
    ResponseEntity<List<FullBookItem>> findByTitle(@PathVariable String title) {
        List<FullBookItem> byTitle = bookItemCatalog.findByTitle(title);
        if (byTitle != null) {
            return ResponseEntity.ok(byTitle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/author/name/{name}/surname/{surname}")
    ResponseEntity<List<FullBookItem>> findByTitle(@PathVariable String name, @PathVariable String surname) {
        List<FullBookItem> byAuthor = bookItemCatalog.findByAuthor(name, surname);
        if (byAuthor != null) {
            return ResponseEntity.ok(byAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    List<FullBookItem> all() {
        return bookItemCatalog.listAll();
    }
}
