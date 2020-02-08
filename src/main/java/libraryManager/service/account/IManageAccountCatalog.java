package libraryManager.service.account;

import libraryManager.entity.Account;

public interface IManageAccountCatalog {
    Boolean add(Account account);
    Boolean suspend(Long id);
    Boolean unSuspend(Long id);
}
