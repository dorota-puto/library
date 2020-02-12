package libraryManager.controllers.librarian;

import libraryManager.model.BookItemDTO;
import libraryManager.service.book.BookItemCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookItemManageController {

    @Autowired
    private BookItemCatalog bookItemCatalog;

    @CrossOrigin
    @GetMapping("/library/bookitems")
    List<BookItemDTO> all() {
        return bookItemCatalog.listAll();
    }

    @PostMapping("/library/bookitem")
    ResponseEntity<Void> newBookItem(@RequestBody BookItemDTO newBookItem) {
        if (bookItemCatalog.add(newBookItem)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/bookitem/{rfidTag}")
    BookItemDTO bookItem(@PathVariable String rfidTag) throws Exception {
        if (bookItemCatalog.findByRfidTag(rfidTag) != null) {
            return bookItemCatalog.findByRfidTag(rfidTag);
        } else throw new Exception();
    }

    @DeleteMapping("library/bookitem/{rfidTag}")
    void deleteBookItem(@PathVariable String rfidTag) {
        bookItemCatalog.remove(rfidTag);
    }
}
