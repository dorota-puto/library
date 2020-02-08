package libraryManager.repository;

import libraryManager.entity.AuthorEntity;

import java.util.Optional;

public interface AuthorRepository {
    boolean save(AuthorEntity authorEntity);

    Optional<AuthorEntity> findById(Long id);

    int deleteById(Long id);
}
