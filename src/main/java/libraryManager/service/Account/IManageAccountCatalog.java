package libraryManager.service.Account;

import libraryManager.model.Account;

public interface IManageAccountCatalog {
    Boolean add(Account account);
    // todo: change return type to boolean in case of suspend and unSuspend
    Account suspend(Long id);
    Account unSuspend(Long id);
}
