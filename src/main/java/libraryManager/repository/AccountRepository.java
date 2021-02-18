package libraryManager.repository;

import libraryManager.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    int count();

    boolean save(Account account);

    boolean updateActive(Long accountID, boolean active);

    List<Account> findAll();

    Optional<Account> findById(Long id);

    int deleteById(Long id);
}
