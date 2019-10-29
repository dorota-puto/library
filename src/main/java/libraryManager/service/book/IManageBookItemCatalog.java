package libraryManager.service.book;

import libraryManager.model.BookItem;

public interface IManageBookItemCatalog {
    Boolean remove(String rfidTag);
    Boolean add(BookItem bookItem);
}
