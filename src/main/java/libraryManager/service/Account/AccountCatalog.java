package libraryManager.service.Account;
// todo: package should be lowercase, check also other places

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
    public Account suspend(Long id) {
        if (accountById.containsKey(id)) {
            accountById.get(id).setState(AccountState.SUSPENDED);
        } else {
            throw new IllegalArgumentException();
        }
        return accountById.get(id);
    }

    @Override
    public Account unSuspend(Long id) {
        if (accountById.containsKey(id)) {
            Account account = accountById.get(id);
            account.setState(AccountState.ACTIVE);
            return account;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Account findById(Long id) {
        // todo: get by default returns null when key not present
        return accountById.getOrDefault(id, null);
    }
}
