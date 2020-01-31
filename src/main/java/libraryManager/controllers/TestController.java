package libraryManager.controllers;

import libraryManager.repository.JdbcAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private JdbcAuthorRepository authorRepository;

    @GetMapping("/test")
    public String test() {
        return "" + authorRepository.count();
    }
}
