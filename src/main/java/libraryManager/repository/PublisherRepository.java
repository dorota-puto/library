package libraryManager.repository;

import libraryManager.entity.PublisherEntity;

import java.util.Optional;

public interface PublisherRepository {
    boolean save(PublisherEntity publisherEntity);

    Optional<PublisherEntity> findById(Long id);

    int deleteById(Long id);
}
