package libraryManager.service.account;

import libraryManager.model.Account;
import libraryManager.model.AccountState;

import java.util.HashMap;
import java.util.Map;

public class AccountCatalog implements ISearchAccountCatalog, IManageAccountCatalog {

    private Map<Long, Account> accountById = new HashMap<>();

    @Override
    public Boolean add(Account account) {
        if (!accountById.containsKey(account.getAccountId())) {
            accountById.put(account.getAccountId(), account);
            return true;
        }
        return false;
    }

    @Override
    public Boolean suspend(Long id) {
        if (accountById.containsKey(id)) {
            accountById.get(id).setState(AccountState.SUSPENDED);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Boolean unSuspend(Long id) {
        if (accountById.containsKey(id)) {
            Account account = accountById.get(id);
            account.setState(AccountState.ACTIVE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Account findById(Long id) {
        return accountById.get(id);
    }
}
