package libraryManager.service.Account;

import libraryManager.model.Account;

public interface ISearchAccountCatalog {
    Account findById(Long id);
}
