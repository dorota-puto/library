package libraryManager.service.account;

import libraryManager.entity.Account;
import libraryManager.repository.AccountRepository;

import java.util.List;
import java.util.Optional;


//todo: remove me
public class AccountCatalog implements ISearchAccountCatalog, IManageAccountCatalog {

    private AccountRepository accountRepository;

    public AccountCatalog(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAll() {
        return accountRepository.findAll();
    }

    @Override
    public Boolean add(Account account) {
        return accountRepository.save(account);
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
        Optional<Account> accountEntity = accountRepository.findById(id);
        return accountEntity.orElse(null);
    }
}
