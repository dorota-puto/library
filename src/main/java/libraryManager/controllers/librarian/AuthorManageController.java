package libraryManager.controllers.librarian;


import libraryManager.entity.Author;
import libraryManager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorManageController {

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping("/library/author")
    ResponseEntity<Void> newAuthor(@RequestBody Author author) {
        if (authorRepository.save(author)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/authors")
    List<Author> all() {return authorRepository.findAll();}


    @GetMapping("/library/author/{id}")
    ResponseEntity<Author> author(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("library/author/{id}")
    void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }

}