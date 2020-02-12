package libraryManager.controllers.librarian;

import libraryManager.entity.BookItem;
import libraryManager.repository.BookItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookItemManageController {

    @Autowired
    private BookItemRepository bookItemRepository;

    @PostMapping("/library/bookItem")
    ResponseEntity<Void> newBookItem(@RequestBody BookItem bookItem) {
        if (bookItemRepository.save(bookItem)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/bookItems")
    List<BookItem> all() {return bookItemRepository.findAll();}


    @GetMapping("/library/bookItem/{rfidTag}")
    ResponseEntity<BookItem> bookItem(@PathVariable String rfidTag) {
        return bookItemRepository.findByRfidTag(rfidTag)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("library/bookItem/{rfidTag}")
    void deleteBookItem(@PathVariable String rfidTag) {
        bookItemRepository.deleteByRfidTag(rfidTag);
    }

}
