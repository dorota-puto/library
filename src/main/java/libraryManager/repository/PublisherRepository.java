package libraryManager.repository;

import libraryManager.entity.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {
    boolean save(Publisher publisher);

    Optional<Publisher> findById(Long id);

    List<Publisher> findAll();

    int deleteById(Long id);
}
