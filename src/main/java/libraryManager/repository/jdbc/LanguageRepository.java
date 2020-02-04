package libraryManager.repository.jdbc;

import libraryManager.entity.LanguageEntity;

import java.util.Optional;

public interface LanguageRepository {
    boolean save(LanguageEntity languageEntity);

    Optional<LanguageEntity> findById(Long id);

    int deleteById(Long id);
}
