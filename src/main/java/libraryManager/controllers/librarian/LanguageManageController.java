package libraryManager.controllers.librarian;

import libraryManager.entity.Language;
import libraryManager.repository.jdbc.JdbcLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguageManageController {

    @Autowired
    private JdbcLanguageRepository languageRepository;


    @PostMapping("/library/language")
    ResponseEntity<Void> newLanguage(@RequestBody Language language) {
        if (languageRepository.save(language)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/library/languages")
    List<Language> all() {
        return languageRepository.findAll();
    }
    @GetMapping("/library/language/{id}")
    ResponseEntity<Language> publisher(@PathVariable Long id) {
        return languageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("library/language/{id}")
    void deleteLanguage(@PathVariable Long id) {
        languageRepository.deleteById(id);
    }
}
