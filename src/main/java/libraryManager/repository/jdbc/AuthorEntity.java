package libraryManager.repository.jdbc;

import java.util.Optional;

public interface AuthorEntity {
    boolean save(libraryManager.entity.AuthorEntity authorEntity);

    Optional<libraryManager.entity.AuthorEntity> findById(Long id);

    int deleteById(Long id);
}
