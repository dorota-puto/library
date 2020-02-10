package libraryManager.repository;

import libraryManager.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    boolean save(Author author);

    Optional<Author> findById(Long id);

    int deleteById(Long id);
}
