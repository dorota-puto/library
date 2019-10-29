package libraryManager.service.account;

import libraryManager.model.Account;

public interface IManageAccountCatalog {
    Boolean add(Account account);
    Boolean suspend(Long id);
    Boolean unSuspend(Long id);
}
