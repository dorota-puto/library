package libraryManager.repository;

import libraryManager.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    boolean save(Book book);

    Optional<Book> findByIsbn(Long isbn);

    List<Book> findAll();

    int deleteByIsbn(Long isbn);
}
