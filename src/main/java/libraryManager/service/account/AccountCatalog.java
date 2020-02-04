package libraryManager.service.account;

import libraryManager.entity.AccountEntity;
import libraryManager.model.Account;
import libraryManager.model.AccountState;
import libraryManager.repository.jdbc.AccountRepository;
import libraryManager.repository.jdbc.JdbcAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public class AccountCatalog implements ISearchAccountCatalog, IManageAccountCatalog {

    private AccountRepository accountRepository;

    public AccountCatalog(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAll() {
        return accountRepository.findAll().stream()
                .map(e -> new Account(e.getAccountID(), e.getName(), e.getActive() ? AccountState.ACTIVE : AccountState.SUSPENDED))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean add(Account account) {
        AccountEntity accountEntity = new AccountEntity(account.getAccountName(), account.getState().equals(AccountState.ACTIVE));
        return accountRepository.save(accountEntity);
    }

    @Override
    public Boolean suspend(Long id) {
        return accountRepository.updateActive(id, false);
    }

    @Override
    public Boolean unSuspend(Long id) {
        return accountRepository.updateActive(id, true);
    }

    @Override
    public Account findById(Long id) {
        Optional<AccountEntity> accountEntity = accountRepository.findById(id);
        return accountEntity.map(entity -> new Account(id, entity.getName(), entity.getActive() ? AccountState.ACTIVE : AccountState.SUSPENDED)).orElse(null);
    }
}
