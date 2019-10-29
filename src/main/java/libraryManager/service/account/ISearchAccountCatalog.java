package libraryManager.service.account;

import libraryManager.model.Account;

public interface ISearchAccountCatalog {
    Account findById(Long id);
}
