package libraryManager.service.Book;

import libraryManager.model.BookItem;

public interface IManageBookItemCatalog {
    Boolean remove(String rfidTag);
    Boolean add(BookItem bookItem);
}
