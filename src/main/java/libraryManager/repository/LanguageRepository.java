package libraryManager.repository;

import libraryManager.entity.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository {
    boolean save(Language language);

    Optional<Language> findById(Long id);

    List<Language> findAll();

    int deleteById(Long id);
}
