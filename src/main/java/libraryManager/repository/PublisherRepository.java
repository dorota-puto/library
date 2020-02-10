package libraryManager.repository;

import libraryManager.entity.Publisher;

import java.util.Optional;

public interface PublisherRepository {
    boolean save(Publisher publisher);

    Optional<Publisher> findById(Long id);

    int deleteById(Long id);
}
