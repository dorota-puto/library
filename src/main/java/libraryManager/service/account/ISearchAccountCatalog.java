package libraryManager.service.account;

import libraryManager.entity.Account;

public interface ISearchAccountCatalog {
    Account findById(Long id);
}
