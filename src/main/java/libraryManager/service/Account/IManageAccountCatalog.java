package libraryManager.service.Account;

import libraryManager.model.Account;

public interface IManageAccountCatalog {
    Boolean add(Account account);
    Account suspend(Long id);
    Account unSuspend(Long id);
}
