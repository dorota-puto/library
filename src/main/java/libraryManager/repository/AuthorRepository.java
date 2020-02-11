package libraryManager.repository;

import libraryManager.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    boolean save(Author author);

    Optional<Author> findById(Long id);

    List<Author> findAll();

    int deleteById(Long id);
}
