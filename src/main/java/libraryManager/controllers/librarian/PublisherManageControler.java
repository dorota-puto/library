package libraryManager.controllers.librarian;

import libraryManager.entity.Publisher;
import libraryManager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PublisherManageControler {

    @Autowired
    private PublisherRepository publisherRepository;

    @PostMapping("/library/publisher")
    ResponseEntity<Void> newPublisher(@RequestBody Publisher publisher) {
        if (publisherRepository.save(publisher)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/publishers")
    List<Publisher> all() {
        return publisherRepository.findAll();
    }
    @GetMapping("/library/publisher/{id}")
    ResponseEntity<Publisher> publisher(@PathVariable Long id) {
        return publisherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("library/publisher/{id}")
    void deletePublisher(@PathVariable Long id) {
        publisherRepository.deleteById(id);
    }

}
