package libraryManager.controllers;

import libraryManager.entity.BookWriteEntity;
import libraryManager.repository.AuthorRepository;
import libraryManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;



    @GetMapping("/test")
    public String test() {
        return "" + bookRepository.findAll();
    }
}
