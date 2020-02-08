package libraryManager.repository;

import java.util.Optional;

public interface AuthorRepository {
    boolean save(libraryManager.entity.AuthorEntity authorEntity);

    Optional<libraryManager.entity.AuthorEntity> findById(Long id);

    int deleteById(Long id);
}
