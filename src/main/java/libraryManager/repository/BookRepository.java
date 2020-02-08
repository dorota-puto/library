package libraryManager.repository;

import libraryManager.entity.BookReadEntity;
import libraryManager.entity.BookWriteEntity;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    int count();

    boolean save(BookWriteEntity bookWriteEntity);

    List<BookReadEntity> findAll();

    Optional<BookReadEntity> findByIsbn(Long isbn);

    int deleteByIsbn(Long isbn);
}
