package libraryManager.repository;

import libraryManager.entity.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    int count();

    boolean save(AccountEntity accountEntity);

    boolean updateActive(Long accountID, boolean active);

    List<AccountEntity> findAll();

    Optional<AccountEntity> findById(Long id);

    int deleteById(Long id);
}
